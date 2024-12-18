package develop.grassserver.grass.application.service;

import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.grass.application.dto.MemberStudyInfoDTO;
import develop.grassserver.member.application.service.MemberService;
import develop.grassserver.member.domain.entity.Member;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberStudyInfoService {

    private final MemberService memberService;
    private final GrassService grassService;
    private final RedisService redisService;

    public MemberStudyInfoDTO getMemberStudyInfo(List<Long> memberIds) {
        List<Member> members = memberService.findAllMembersByIds(memberIds);
        Map<Long, String> studyTimes = grassService.getOthersTodayStudyTime(memberIds);
        Map<Long, Boolean> studyStatuses = redisService.getOthersStudyStatus(memberIds);

        return new MemberStudyInfoDTO(members, studyTimes, studyStatuses);
    }
}
