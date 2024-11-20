package develop.grassserver.friend.application.service;

import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.friend.application.exception.AlreadyFriendRequestException;
import develop.grassserver.friend.application.exception.ExistFriendRelationException;
import develop.grassserver.friend.application.exception.NotExistFriendRelationException;
import develop.grassserver.friend.domain.entity.Friend;
import develop.grassserver.friend.domain.entity.FriendRequestStatus;
import develop.grassserver.friend.infrastructure.repository.FriendRepository;
import develop.grassserver.friend.presentation.dto.FindAllFriendsResponse;
import develop.grassserver.friend.presentation.dto.RequestFriendRequest;
import develop.grassserver.friend.presentation.dto.SendCheerUpEmojiRequest;
import develop.grassserver.friend.presentation.dto.SendCheerUpMessageRequest;
import develop.grassserver.grass.application.service.GrassService;
import develop.grassserver.member.application.service.MemberService;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.notification.application.service.EmojiNotificationService;
import develop.grassserver.notification.application.service.MessageNotificationService;
import develop.grassserver.requestNotification.domain.entity.FriendRequestNotification;
import develop.grassserver.requestNotification.infrastructure.repository.FriendRequestNotificationRepository;
import java.util.List;
import java.util.Map;
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

    private final GrassService grassService;
    private final RedisService redisService;
    private final MemberService memberService;
    private final EmojiNotificationService emojiNotificationService;
    private final MessageNotificationService messageNotificationService;

    private final FriendRepository friendRepository;
    private final FriendRequestNotificationRepository friendRequestNotificationRepository;

    public FindAllFriendsResponse findAllFriends(Member me) {
        List<Friend> friends = friendRepository.findAllMyFriends(me.getId());
        List<Long> friendIds = getFriendIds(me, friends);

        List<Member> members = memberService.findAllMembersByIds(friendIds);

        Map<Long, String> studyTimes = grassService.getFriendsTodayStudyTime(friendIds);

        Map<Long, Boolean> friendStudyStatus = redisService.getFriendStudyStatus(friendIds);
        return FindAllFriendsResponse.from(members, studyTimes, friendStudyStatus);
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
    public void requestFriend(Member member, RequestFriendRequest request) {
        Member me = memberService.findMemberById(member.getId());
        Member other = memberService.findMemberById(request.memberId());

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
}
