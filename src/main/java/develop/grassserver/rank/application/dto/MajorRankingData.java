package develop.grassserver.rank.application.dto;

import java.time.Duration;

public record MajorRankingData(
        String majorName,
        int memberCount,
        Duration majorTotalStudyTime,
        int majorGrassScore
) {
}
