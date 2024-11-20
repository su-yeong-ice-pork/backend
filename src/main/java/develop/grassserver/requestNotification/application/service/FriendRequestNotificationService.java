package develop.grassserver.requestNotification.application.service;

import develop.grassserver.common.BaseEntity;
import develop.grassserver.friend.application.service.FriendService;
import develop.grassserver.friend.domain.entity.Friend;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.requestNotification.domain.entity.FriendRequestNotification;
import develop.grassserver.requestNotification.infrastructure.repository.FriendRequestNotificationRepository;
import develop.grassserver.requestNotification.presentation.dto.FindAllFriendRequestsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendRequestNotificationService {

    private final FriendService friendService;

    private final FriendRequestNotificationRepository friendRequestNotificationRepository;

    public FindAllFriendRequestsResponse findAllFriendRequests(Member member) {
        List<Friend> friends = friendService.findAllNotAcceptedFriendRelations(member.getId());
        List<Long> friendRelationIds = getFriendRelationIds(friends);

        List<FriendRequestNotification> notifications = friendRequestNotificationRepository.findAllByFriends(friendRelationIds);
        return FindAllFriendRequestsResponse.from(notifications);
    }

    private List<Long> getFriendRelationIds(List<Friend> friends) {
        return friends.stream()
                .map(BaseEntity::getId)
                .toList();
    }
}
