package develop.grassserver.notification.infrastructure.repository;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.notification.domain.entity.MessageNotification;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageNotificationRepository extends JpaRepository<MessageNotification, Long> {

    @Query("select count(m) "
            + "from MessageNotification m "
            + "where (m.sender = :sender and m.receiver = :receiver) "
            + "and m.createdAt between :startOfDay and :endOfDay")
    long findAllBySenderAndReceiverAndToday(
            @Param("sender") Member sender,
            @Param("receiver") Member receiver,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}
