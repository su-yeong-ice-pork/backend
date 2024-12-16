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

    /*
        MemberStudyInfo 서비스 클래스를 만들어서 멤버 정보 + 공부 중 조회 로직을 분리했습니다.
        GrassMemberService도 MemberStudyInfoService와 같은 서비스를 주입받고 있어서 신경쓰이지만, 책임 분리를 위해 합치진 않았습니다.
     */
    public MemberStudyInfoDTO getMemberStudyInfo(List<Long> memberIds) {
        List<Member> members = memberService.findAllMembersByIds(memberIds);
        Map<Long, String> studyTimes = grassService.getOthersTodayStudyTime(memberIds);
        Map<Long, Boolean> studyStatuses = redisService.getOthersStudyStatus(memberIds);

        return new MemberStudyInfoDTO(members, studyTimes, studyStatuses);
    }
}