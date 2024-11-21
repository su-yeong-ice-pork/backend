package develop.grassserver.randomStudy.presentation.dto;

import develop.grassserver.grass.application.dto.MemberStudyInfoDTO;
import java.util.List;

public record FindAllRandomStudyMembersResponse(
        List<ParticipantDTO> participants
) {

    public static FindAllRandomStudyMembersResponse from(MemberStudyInfoDTO studyInfo) {
        return new FindAllRandomStudyMembersResponse(
                studyInfo.members().stream()
                        .map(member ->
                                new ParticipantDTO(
                                        member.getId(),
                                        member.getName(),
                                        member.getProfile().getImage(),
                                        studyInfo.studyTimes().get(member.getId()),
                                        studyInfo.studyStatuses().get(member.getId())
                                )
                        )
                        .toList()
        );
    }

    public record ParticipantDTO(
            Long memberId,
            String name,
            String profileImage,
            String todayStudyTime,
            boolean studyStatus
    ) {
    }
}
