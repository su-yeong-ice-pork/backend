package develop.grassserver.requestNotification.infrastructure.repository;

import develop.grassserver.requestNotification.domain.entity.RequestNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestNotificationRepository extends JpaRepository<RequestNotification, Long> {
}
