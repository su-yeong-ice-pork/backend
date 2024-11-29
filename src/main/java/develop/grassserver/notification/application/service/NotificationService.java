package develop.grassserver.notification.application.service;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.notification.domain.entity.Notification;
import develop.grassserver.notification.infrastructure.repository.NotificationRepository;
import develop.grassserver.notification.presentation.dto.FindAllEmojiAndMessageNotificationsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public FindAllEmojiAndMessageNotificationsResponse findAllEmojiAndMessageNotifications(Member member) {
        List<Notification> notifications = notificationRepository.findByReceiverIdWithSender(member.getId());
        return FindAllEmojiAndMessageNotificationsResponse.from(notifications);
    }
}
