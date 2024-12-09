package develop.grassserver.randomStudy.presentation.dto;

import static develop.grassserver.common.utils.duration.DurationUtils.formatHourDuration;

import develop.grassserver.randomStudy.domain.entity.RandomStudy;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record RandomStudyDetailResponse(
        Long id,
        String studyName,
        String attendanceTime,
        Long totalStudyTime
) {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("a h시", Locale.KOREA);

    public static RandomStudyDetailResponse from(RandomStudy randomStudy) {
        return new RandomStudyDetailResponse(
                randomStudy.getId(),
                randomStudy.getName(),
                randomStudy.getAttendanceTime().format(TIME_FORMATTER),
                formatHourDuration(randomStudy.getTotalStudyTime())
        );
    }
}
