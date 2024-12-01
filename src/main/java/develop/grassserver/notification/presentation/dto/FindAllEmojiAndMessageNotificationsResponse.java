package develop.grassserver.notification.presentation.dto;

import develop.grassserver.common.utils.DateTimeUtils;
import develop.grassserver.notification.domain.entity.Notification;
import java.time.LocalDateTime;
import java.util.List;

public record FindAllEmojiAndMessageNotificationsResponse(List<NotificationDTO> notifications) {

    public static FindAllEmojiAndMessageNotificationsResponse from(List<Notification> notifications) {
        return new FindAllEmojiAndMessageNotificationsResponse(
                notifications.stream()
                        .map(FindAllEmojiAndMessageNotificationsResponse::toNotificationDTO)
                        .toList()
        );
    }

    private static NotificationDTO toNotificationDTO(Notification notification) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime createdAt = notification.getCreatedAt();

        boolean isToday = DateTimeUtils.isSameDay(today, createdAt);
        String time = DateTimeUtils.formatNotificationTime(createdAt);
        String date = DateTimeUtils.formatNotificationDate(createdAt);

        return notification.toDTO(isToday, time, date);
    }

    public record NotificationDTO(
            Long id,
            String sender,
            String type,
            int emojiNumber,
            String message,
            boolean isToday,
            String time,
            String date
    ) {
    }
}
