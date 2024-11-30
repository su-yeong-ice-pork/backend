package develop.grassserver.randomStudy.presentation.dto;

import java.util.List;

public record RandomStudyMembersResponse(
        List<ParticipantResponse> participants) {
}
