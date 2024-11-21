package develop.grassserver.study.presentation.dto;

public record StudyDetailResponse(
        Long id,
        String studyName,
        String goalMessage,
        Long goalTime,
        Long totalStudyTime,
        String inviteCode
) {
}
