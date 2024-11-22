package develop.grassserver.notification.infrastructure.repository;

import develop.grassserver.notification.domain.entity.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByReceiverId(Long id);
}
