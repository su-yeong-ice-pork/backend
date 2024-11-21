package develop.grassserver.study.presentation.dto;

import develop.grassserver.grass.application.dto.MemberStudyInfoDTO;
import develop.grassserver.study.domain.entity.StudyRole;
import java.util.List;
import java.util.Map;

public record FindAllStudyMembersResponse(
        List<ParticipantDTO> participants
) {

    public static FindAllStudyMembersResponse from(MemberStudyInfoDTO studyInfo, Map<Long, StudyRole> roleMap) {
        return new FindAllStudyMembersResponse(
                studyInfo.members().stream()
                        .map(member ->
                                new ParticipantDTO(
                                        member.getId(),
                                        member.getName(),
                                        member.getProfile().getImage(),
                                        studyInfo.studyTimes().get(member.getId()),
                                        studyInfo.studyStatuses().get(member.getId()),
                                        roleMap.get(member.getId()) == StudyRole.LEADER
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
            boolean studyStatus,
            boolean isLeader
    ) {
    }
}
