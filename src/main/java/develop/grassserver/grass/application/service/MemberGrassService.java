package develop.grassserver.grass.application.service;

import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.grass.domain.entity.Grass;
import develop.grassserver.grass.presentation.dto.MemberStreakResponse;
import develop.grassserver.grass.presentation.dto.MemberTotalStreakResponse;
import develop.grassserver.grass.presentation.dto.MonthlyGrassResponse;
import develop.grassserver.grass.presentation.dto.MonthlyTotalGrassResponse;
import develop.grassserver.grass.presentation.dto.YearlyGrassResponse;
import develop.grassserver.grass.presentation.dto.YearlyTotalGrassResponse;
import develop.grassserver.member.application.exception.UnauthorizedException;
import develop.grassserver.member.application.service.MemberService;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.member.domain.entity.StudyRecord;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberGrassService {

    private final RedisService redisService;
    private final GrassService grassService;
    private final MemberService memberService;

    public void startStudyRecord(Long id, Member member) {
        Member findMember = memberService.findMemberById(id);
        if (!member.isMyName(findMember.getName())) {
            throw new UnauthorizedException();
        }

        redisService.saveStudyStatus(findMember.getId());
    }

    @Transactional
    public void createAttendance(Member member) {
        Member memberById = memberService.findMemberById(member.getId());
        Grass grass = grassService.createGrass(member);

        if (grass.getCurrentStreak() > memberById.getStudyRecord().getTopStreak()) {
            memberById.getStudyRecord().updateTopStreak(grass.getCurrentStreak());
        }
    }

    public MemberStreakResponse getMemberStreak(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        Grass grass = grassService.findDayGrassByMemberId(memberId);

        StudyRecord studyRecord = member.getStudyRecord();
        int topStreak = studyRecord.getTopStreak();
        long totalStudyTime = DurationUtils.formatHourDuration(studyRecord.getTotalStudyTime());

        int currentStreak = Optional.ofNullable(grass).map(Grass::getCurrentStreak).orElse(0);

        return new MemberStreakResponse(currentStreak, topStreak, totalStudyTime);
    }

    public MemberTotalStreakResponse getMemberTotalStreak(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        Long grassCount = grassService.getTotalGrassCount(memberId);

        StudyRecord studyRecord = member.getStudyRecord();
        long totalStudyTime = DurationUtils.formatHourDuration(studyRecord.getTotalStudyTime());

        LocalDate startDate = member.getCreatedAt().toLocalDate();

        return new MemberTotalStreakResponse(startDate, grassCount, totalStudyTime);
    }

    public YearlyTotalGrassResponse getYearlyGrass(Long memberId, int year) {
        Member member = memberService.findMemberById(memberId);
        return new YearlyTotalGrassResponse(year, grassService.findYearlyGrassByMemberId(member, year).stream()
                .map(grass -> new YearlyGrassResponse(grass.getId(), grass.getCreatedAt().getMonthValue(),
                        grass.getCreatedAt().getDayOfMonth(), DurationUtils.formatHourDuration(grass.getStudyTime())))
                .toList());
    }

    public MonthlyTotalGrassResponse getMonthlyGrass(Long memberId, int year, int month) {
        Member member = memberService.findMemberById(memberId);
        return new MonthlyTotalGrassResponse(year, month,
                grassService.findMonthlyGrassByMemberId(member, year, month).stream()
                        .map(grass -> new MonthlyGrassResponse(grass.getId(), grass.getCreatedAt().getDayOfMonth(),
                                DurationUtils.formatHourDuration(grass.getStudyTime()), grass.getGrassScore()))
                        .toList());
    }
}
