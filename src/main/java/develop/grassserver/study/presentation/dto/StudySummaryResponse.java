package develop.grassserver.study.presentation.dto;

public record StudySummaryResponse(
        Long id,
        String studyName,
        int memberCount,
        Long totalStudyTime
) {
}
