package develop.grassserver.notification.presentation.dto;

import develop.grassserver.common.utils.DateTimeUtils;
import develop.grassserver.notification.domain.entity.EmojiNotification;
import develop.grassserver.notification.domain.entity.MessageNotification;
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

        if (notification instanceof EmojiNotification emojiNotification) {
            return new NotificationDTO(
                    notification.getId(),
                    "emoji",
                    emojiNotification.getEmojiNumber(),
                    null,
                    isToday,
                    time,
                    date
            );
        } else if (notification instanceof MessageNotification messageNotification) {
            return new NotificationDTO(
                    notification.getId(),
                    "message",
                    -1,
                    messageNotification.getMessage(),
                    isToday,
                    time,
                    date
            );
        }
        throw new IllegalArgumentException("예기치 못한 오류가 발생하였습니다.");
    }

    public record NotificationDTO(
            Long id,
            String type,
            int emojiNumber,
            String message,
            boolean isToday,
            String time,
            String date
    ) {
    }
}
