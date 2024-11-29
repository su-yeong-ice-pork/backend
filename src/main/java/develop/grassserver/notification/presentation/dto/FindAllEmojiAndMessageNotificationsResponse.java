package develop.grassserver.notification.presentation.dto;

import develop.grassserver.common.utils.duration.DurationUtils;
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

        boolean isToday = isSameDay(today, createdAt);
        String time = DurationUtils.formatNotificationTime(createdAt);
        String date = DurationUtils.formatNotificationDate(createdAt);

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

    private static boolean isSameDay(LocalDateTime today, LocalDateTime createdAt) {
        return today.getYear() == createdAt.getYear()
                && today.getMonthValue() == createdAt.getMonthValue()
                && today.getDayOfMonth() == createdAt.getDayOfMonth();
    }

    private static String formatTime(LocalDateTime createdAt) {
        int hour = createdAt.getHour();
        int minute = createdAt.getMinute();

        if (hour > 12) {
            return String.format("오늘 오후 %02d:%02d", hour - 12, minute);
        } else if (hour == 12) {
            return String.format("오늘 오후 %02d:%02d", hour, minute);
        } else {
            return String.format("오늘 오전 %02d:%02d", hour, minute);
        }
    }

    private static String formatDate(LocalDateTime createdAt) {
        return String.format("%02d월 %02d일", createdAt.getMonthValue(), createdAt.getDayOfMonth());
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
