package develop.grassserver.notification.application.service;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.notification.application.exception.ExceedSendMessageCountException;
import develop.grassserver.notification.domain.entity.MessageNotification;
import develop.grassserver.notification.infrastructure.repository.MessageNotificationRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageNotificationService {

    private final MessageNotificationRepository messageNotificationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveMessageNotification(Member me, Member other, String message) {
        checkTodaySentMessageCount(me, other);

        MessageNotification messageNotification = createMessageNotification(me, other, message);
        messageNotificationRepository.save(messageNotification);
    }

    private void checkTodaySentMessageCount(Member me, Member other) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59, 999999999);
        long todaySentMessageCount =
                messageNotificationRepository.findAllBySenderAndReceiverAndToday(me, other, startOfDay, endOfDay);
        if (todaySentMessageCount >= 2) {
            throw new ExceedSendMessageCountException();
        }
    }

    private MessageNotification createMessageNotification(Member me, Member other, String message) {
        return new MessageNotification(me, other, message);
    }
}
