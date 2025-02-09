package develop.grassserver.common.utils.duration;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class DurationUtils {

    public static final int SECONDS_IN_MINUTE = 60;
    public static final int SECONDS_IN_HOUR = 3600;

    private static final DateTimeFormatter DURATION_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("[H][HH]")
            .appendLiteral(':')
            .appendPattern("mm")
            .appendLiteral(':')
            .appendPattern("ss")
            .toFormatter();

    public static Duration parseDuration(String timeStr) {
        TemporalAccessor accessor = DURATION_FORMATTER.parse(timeStr);

        long hours = accessor.getLong(ChronoField.HOUR_OF_DAY);
        long minutes = accessor.getLong(ChronoField.MINUTE_OF_HOUR);
        long seconds = accessor.getLong(ChronoField.SECOND_OF_MINUTE);

        return Duration.ofHours(hours)
                .plusMinutes(minutes)
                .plusSeconds(seconds);
    }

    public static String formatDuration(Duration duration) {
        long totalSeconds = duration.getSeconds();
        long hours = totalSeconds / SECONDS_IN_HOUR;
        long minutes = (totalSeconds % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE;
        long seconds = totalSeconds % SECONDS_IN_MINUTE;
        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }

    public static long formatHourDuration(Duration duration) {
        return duration.getSeconds() / 3600;
    }

    public static String formatHourAndMinute(Duration duration) {
        long totalSeconds = duration.getSeconds();
        long hours = totalSeconds / SECONDS_IN_HOUR;
        long minutes = (totalSeconds % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE;
        return String.format("%02d시간 %02d분", hours, minutes);
    }

    public static String formatHourAndMinuteByLongTypeTime(long totalSeconds) {
        long hours = totalSeconds / SECONDS_IN_HOUR;
        long minutes = (totalSeconds % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE;
        return String.format("%02d시간 %02d분", hours, minutes);
    }
}
