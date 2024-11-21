package develop.grassserver.randomStudy.presentation.dto;

public record RandomStudyDetailResponse(
        Long id,
        String studyName,
        String attendanceTime,
        Long totalStudyTime
) {
}
