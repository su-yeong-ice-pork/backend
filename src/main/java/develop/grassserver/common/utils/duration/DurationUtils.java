package develop.grassserver.common.utils.duration;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class DurationUtils {

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
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }

    public static int formatHourDuration(Duration duration) {
        return (int) duration.getSeconds() / 3600;
    }
}
