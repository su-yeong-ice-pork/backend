package develop.grassserver.rank.application.dto;

public record MajorRankingData(
        String majorName,
        long memberCount,
        long majorTotalStudyTime,
        long majorGrassScore
) {
}
