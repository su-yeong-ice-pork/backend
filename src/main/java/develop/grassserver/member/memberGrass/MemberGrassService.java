package develop.grassserver.member.memberGrass;

import develop.grassserver.grass.Grass;
import develop.grassserver.grass.GrassService;
import develop.grassserver.member.Member;
import develop.grassserver.member.MemberService;
import develop.grassserver.member.StudyRecord;
import develop.grassserver.member.memberGrass.dto.MemberStreakResponse;
import develop.grassserver.member.memberGrass.dto.MonthlyGrassResponse;
import develop.grassserver.member.memberGrass.dto.MonthlyTotalGrassResponse;
import develop.grassserver.member.memberGrass.dto.YearlyGrassResponse;
import develop.grassserver.member.memberGrass.dto.YearlyTotalGrassResponse;
import develop.grassserver.utils.duration.DurationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberGrassService {
    private final MemberService memberService;
    private final GrassService grassService;

    public MemberStreakResponse getMemberStreak(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        Grass grass = grassService.findDayGrassByMemberId(memberId);

        StudyRecord studyRecord = member.getStudyRecord();
        int topStreak = studyRecord.getTopStreak();
        String totalStudyTimeFormatted = DurationUtils.formatDuration(studyRecord.getTotalStudyTime());

        int currentStreak = (grass != null) ? grass.getCurrentStreak() : 0;

        return new MemberStreakResponse(currentStreak, topStreak, totalStudyTimeFormatted);
    }

    public YearlyTotalGrassResponse getYearlyGrass(Long memberId, int year) {
        Member member = memberService.findMemberById(memberId);
        return new YearlyTotalGrassResponse(
                year,
                grassService.findYearlyGrassByMemberId(member, year).stream()
                        .map(grass -> new YearlyGrassResponse(
                                grass.getId(),
                                grass.getCreatedAt().getMonthValue(),
                                grass.getCreatedAt().getDayOfMonth(),
                                DurationUtils.formatDuration(grass.getStudyTime())
                        ))
                        .toList()
        );
    }

    public MonthlyTotalGrassResponse getMonthlyGrass(Long memberId, int year, int month) {
        Member member = memberService.findMemberById(memberId);
        return new MonthlyTotalGrassResponse(
                year,
                month,
                grassService.findMonthlyGrassByMemberId(member, year, month).stream()
                        .map(grass -> new MonthlyGrassResponse(
                                grass.getId(),
                                grass.getCreatedAt().getDayOfMonth(),
                                DurationUtils.formatDuration(grass.getStudyTime()),
                                grass.getGrassScore()
                        ))
                        .toList()
        );
    }
}
