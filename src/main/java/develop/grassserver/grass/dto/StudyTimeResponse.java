package develop.grassserver.grass.dto;

import java.time.LocalTime;

public record StudyTimeResponse(
        LocalTime todayStudyTime,
        LocalTime totalStudyTime
) {
}
