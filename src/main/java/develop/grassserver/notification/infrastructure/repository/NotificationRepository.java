package develop.grassserver.notification.infrastructure.repository;

import develop.grassserver.notification.domain.entity.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


    @Query("SELECT n FROM Notification n JOIN FETCH n.sender WHERE n.receiver.id = :id")
    List<Notification> findByReceiverIdWithSender(@Param("id") Long id);
}
