package develop.grassserver.randomStudy.presentation.dto;

public record ParticipantResponse(
        Long memberId,
        String name,
        String profileImage,
        String todayStudyTime,
        boolean studyStatus
) {
}
