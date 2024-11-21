package develop.grassserver.randomStudy.presentation.dto;

import java.util.List;

public record FindAllRandomStudyMembersResponse(
        List<ParticipantDTO> participants
) {
    public record ParticipantDTO(
            Long memberId,
            String name,
            String profileImage,
            String todayStudyTime,
            boolean studyStatus
    ) {
    }
}
