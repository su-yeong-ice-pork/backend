package develop.grassserver.grass.application.dto;

import develop.grassserver.member.domain.entity.Member;
import java.util.List;
import java.util.Map;

public record MemberStudyInfoDTO(
        List<Member> members,
        Map<Long, String> studyTimes,
        Map<Long, Boolean> studyStatuses
) {
}
