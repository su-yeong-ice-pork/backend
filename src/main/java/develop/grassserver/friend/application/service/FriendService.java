package develop.grassserver.friend.application.service;

import develop.grassserver.friend.application.exception.ExistFriendRelationException;
import develop.grassserver.friend.application.exception.NotExistFriendRelationException;
import develop.grassserver.friend.domain.entity.Friend;
import develop.grassserver.friend.domain.entity.FriendRequestStatus;
import develop.grassserver.friend.infrastructure.repository.FriendRepository;
import develop.grassserver.friend.presentation.dto.RequestFriendRequest;
import develop.grassserver.friend.presentation.dto.SendCheerUpEmojiRequest;
import develop.grassserver.friend.presentation.dto.SendCheerUpMessageRequest;
import develop.grassserver.member.application.service.MemberService;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.notification.application.service.EmojiNotificationService;
import develop.grassserver.notification.application.service.MessageNotificationService;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final MemberService memberService;
    private final EmojiNotificationService emojiNotificationService;
    private final MessageNotificationService messageNotificationService;

    private final FriendRepository friendRepository;

    @Transactional
    public void requestFriend(Member member, RequestFriendRequest request) {
        Member me = memberService.findMemberById(member.getId());
        Member other = memberService.findMemberById(request.memberId());

        handleFriendRelation(me, other);
    }

    private void handleFriendRelation(Member me, Member other) {
        Optional<Friend> optionalFriend = friendRepository.findFriend(me.getId(), other.getId());
        if (optionalFriend.isPresent()) {
            Friend friend = optionalFriend.get();
            if (friend.getRequestStatus() == FriendRequestStatus.DELETED) {
                friend.reconnect();
                return;
            }
            throw new ExistFriendRelationException();
        }
        createAndSaveFriendRelation(me, other);
    }

    private void createAndSaveFriendRelation(Member me, Member other) {
        Friend friend = createFriendRelation(me, other);
        friendRepository.save(friend);
    }

    private Friend createFriendRelation(Member me, Member other) {
        return Friend.builder()
                .member1(me)
                .member2(other)
                .requestStatus(FriendRequestStatus.PENDING)
                .build();
    }

    @Transactional
    public void deleteFriend(Long id, Member member) {
        Member me = memberService.findMemberById(member.getId());
        Member other = memberService.findMemberById(id);

        Friend friend = friendRepository.findFriend(me.getId(), other.getId())
                .orElseThrow(NotExistFriendRelationException::new);

        friend.disconnect();
    }

    public void sendCheerUpEmoji(Long id, Member member, SendCheerUpEmojiRequest request) {
        Member me = memberService.findMemberById(member.getId());
        Member other = memberService.findMemberById(id);

        checkExistFriendRelation(me, other);

        emojiNotificationService.saveEmojiNotification(me, other, request.emojiNumber());
    }

    public void sendCheerUpMessage(Long id, Member member, @Valid SendCheerUpMessageRequest request) {
        Member me = memberService.findMemberById(member.getId());
        Member other = memberService.findMemberById(id);

        checkExistFriendRelation(me, other);

        messageNotificationService.saveMessageNotification(me, other, request.message());
    }

    private void checkExistFriendRelation(Member me, Member other) {
        Optional<Friend> optionalFriend = friendRepository.findFriend(me.getId(), other.getId());
        if (optionalFriend.isEmpty()) {
            throw new NotExistFriendRelationException();
        }
    }
}
