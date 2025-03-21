package develop.grassserver.friend.application.service;

import develop.grassserver.friend.application.exception.AlreadyFriendRequestException;
import develop.grassserver.friend.application.exception.ExistFriendRelationException;
import develop.grassserver.friend.application.exception.NotExistFriendRelationException;
import develop.grassserver.friend.domain.entity.Friend;
import develop.grassserver.friend.domain.entity.FriendRequestStatus;
import develop.grassserver.friend.infrastructure.repository.FriendRepository;
import develop.grassserver.friend.presentation.dto.FindAllFriendsResponse;
import develop.grassserver.friend.presentation.dto.SendCheerUpEmojiRequest;
import develop.grassserver.friend.presentation.dto.SendCheerUpMessageRequest;
import develop.grassserver.grass.application.dto.MemberStudyInfoDTO;
import develop.grassserver.grass.application.service.MemberStudyInfoService;
import develop.grassserver.member.application.service.MemberService;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.notification.application.service.EmojiNotificationService;
import develop.grassserver.notification.application.service.MessageNotificationService;
import develop.grassserver.requestNotification.domain.entity.FriendRequestNotification;
import develop.grassserver.requestNotification.domain.entity.RequestNotification;
import develop.grassserver.requestNotification.infrastructure.repository.FriendRequestNotificationRepository;
import develop.grassserver.requestNotification.infrastructure.repository.RequestNotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {

    private final MemberService memberService;
    private final MemberStudyInfoService memberStudyInfoService;
    private final EmojiNotificationService emojiNotificationService;
    private final MessageNotificationService messageNotificationService;

    private final FriendRepository friendRepository;

    // 순환 참조 해결 필요
    private final FriendRequestNotificationRepository friendRequestNotificationRepository;
    private final RequestNotificationRepository requestNotificationRepository;

    public FindAllFriendsResponse findAllFriends(Member me) {
        List<Friend> friends = friendRepository.findAllMyFriends(me.getId());
        List<Long> friendIds = getFriendIds(me, friends);

        MemberStudyInfoDTO studyInfo = memberStudyInfoService.getMemberStudyInfo(friendIds);

        return FindAllFriendsResponse.from(studyInfo);
    }

    private List<Long> getFriendIds(Member me, List<Friend> friends) {
        return friends.stream()
                .flatMap(friend ->
                        Stream.of(
                                friend.getMember1().getId(),
                                friend.getMember2().getId()
                        )
                )
                .filter(friendId -> !Objects.equals(me.getId(), friendId))
                .toList();
    }

    @Transactional
    public void requestFriend(Member member, Long otherMemberId) {
        Member me = memberService.findMemberById(member.getId());
        Member other = memberService.findMemberById(otherMemberId);

        handleFriendRelation(me, other);
    }

    private void handleFriendRelation(Member me, Member other) {
        Optional<Friend> optionalFriend = friendRepository.findFriend(me.getId(), other.getId());
        if (optionalFriend.isPresent()) {
            handleExistingFriendRelation(me, other, optionalFriend.get());
        } else {
            createAndSaveFriendRelation(me, other);
        }
    }

    private void handleExistingFriendRelation(Member me, Member other, Friend friend) {
        if (friend.getRequestStatus() == FriendRequestStatus.DELETED) {
            friend.reconnect();
            FriendRequestNotification friendRequestNotification = new FriendRequestNotification(me, other, friend);
            friendRequestNotificationRepository.save(friendRequestNotification);
            return;
        }
        if (friend.getRequestStatus() == FriendRequestStatus.PENDING) {
            throw new AlreadyFriendRequestException();
        }
        throw new ExistFriendRelationException();
    }

    private void createAndSaveFriendRelation(Member me, Member other) {
        Friend friend = createFriendRelation(me, other);
        friendRepository.save(friend);

        FriendRequestNotification friendRequestNotification = new FriendRequestNotification(me, other, friend);
        friendRequestNotificationRepository.save(friendRequestNotification);
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

    public void sendCheerUpMessage(Long id, Member member, SendCheerUpMessageRequest request) {
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

    public List<Friend> findAllNotAcceptedFriendRelations(Long memberId) {
        return friendRepository.findAllPendingFriends(memberId);
    }

    @Transactional
    public void acceptFriendRequest(Long id) {
        FriendRequestNotification friendRequestNotification = friendRequestNotificationRepository.findByIdWithFriend(id)
                .orElseThrow(EntityNotFoundException::new);
        Friend friend = friendRequestNotification.getFriend();

        if (friend.getRequestStatus() == FriendRequestStatus.ACCEPTED) {
            throw new ExistFriendRelationException();
        }

        friend.connect();

        RequestNotification requestNotification = requestNotificationRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        requestNotificationRepository.delete(requestNotification);
    }
}
