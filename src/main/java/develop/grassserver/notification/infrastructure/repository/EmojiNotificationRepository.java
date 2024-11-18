package develop.grassserver.notification.infrastructure.repository;

import develop.grassserver.notification.domain.entity.EmojiNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmojiNotificationRepository extends JpaRepository<EmojiNotification, Long> {
}
