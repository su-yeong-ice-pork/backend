package develop.grassserver.requestNotification.infrastructure.repository;

import develop.grassserver.requestNotification.domain.entity.FriendRequestNotification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRequestNotificationRepository extends JpaRepository<FriendRequestNotification, Long> {

    @Query("select frn "
            + "from FriendRequestNotification frn join fetch frn.friend "
            + "where frn.friend.id in :friendRelationIds")
    List<FriendRequestNotification> findAllByFriends(@Param("friendRelationIds") List<Long> friendRelationIds);
}
