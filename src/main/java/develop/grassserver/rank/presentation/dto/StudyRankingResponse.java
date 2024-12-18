package develop.grassserver.rank.presentation.dto;

public record StudyRankingResponse(
        int rank,
        String studyName,
        int memberCount,
        Long totalStudyTime
) {
}
