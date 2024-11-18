package develop.grassserver.notification.infrastructure.repository;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.notification.domain.entity.EmojiNotification;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmojiNotificationRepository extends JpaRepository<EmojiNotification, Long> {

    @Query("select count(e) "
            + "from EmojiNotification e "
            + "where (e.sender = :sender and e.receiver = :receiver) "
            + "and e.createdAt between :startOfDay and :endOfDay")
    long findAllBySenderAndReceiverAndToday(
            @Param("sender") Member sender,
            @Param("receiver") Member receiver,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}
