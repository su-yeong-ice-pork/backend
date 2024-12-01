package develop.grassserver.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {

    private static final DateTimeFormatter NOTIFICATION_TIME_FORMATTER = DateTimeFormatter.ofPattern("오늘 a hh:mm");
    private static final DateTimeFormatter NOTIFICATION_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일");

    private DateTimeUtils() {
    }

    public static boolean isSameDay(LocalDateTime today, LocalDateTime createdAt) {
        return today.toLocalDate()
                .isEqual(createdAt.toLocalDate());
    }

    public static String formatNotificationTime(LocalDateTime createdAt) {
        return createdAt.format(NOTIFICATION_TIME_FORMATTER);
    }

    public static String formatNotificationDate(LocalDateTime createdAt) {
        return createdAt.format(NOTIFICATION_DATE_FORMATTER);
    }
}
