package develop.grassserver.rank.application.dto;

import java.time.Duration;

public record StudyRankingData(
        Long studyId,
        String studyName,
        Duration totalStudyTime,
        Long memberCount
) {
}
