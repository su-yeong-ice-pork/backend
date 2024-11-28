package develop.grassserver.grass.application.service;

import static develop.grassserver.common.utils.GrassScoreUtil.calculateStudyScore;

import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.grass.application.exception.AlreadyCheckedInException;
import develop.grassserver.grass.application.exception.MissingAttendanceException;
import develop.grassserver.grass.domain.entity.Grass;
import develop.grassserver.grass.infrastructure.repositiory.GrassRepository;
import develop.grassserver.grass.presentation.dto.AttendanceResponse;
import develop.grassserver.grass.presentation.dto.StudyTimeRequest;
import develop.grassserver.grass.presentation.dto.StudyTimeResponse;
import develop.grassserver.member.domain.entity.Member;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrassService {
    private final GrassRepository grassRepository;
    private final Clock clock;

    public boolean isTodayGrassExist(Member member) {
        return findTodayGrass(member.getId()).isPresent();
    }

    private Optional<Grass> findTodayGrass(Long memberId) {
        LocalDate today = LocalDate.now(clock);
        return grassRepository.findByMemberIdAndAttendanceDate(memberId, today);
    }

    @Transactional
    public Grass createGrass(Member member) {

        if (isTodayGrassExist(member)) {
            throw new AlreadyCheckedInException();
        }

        int currentStreak = calculateCurrentStreak(member.getId());
        Grass grass = Grass.builder()
                .member(member)
                .currentStreak(currentStreak)
                .build();
        
        return grassRepository.save(grass);
    }

    private int calculateCurrentStreak(Long memberId) {
        return findYesterdayGrass(memberId)
                .map(grass -> grass.getCurrentStreak() + 1)
                .orElse(1);
    }

    private Optional<Grass> findYesterdayGrass(Long memberId) {
        LocalDate yesterday = LocalDate.now(clock).minusDays(1);
        return grassRepository.findByMemberIdAndAttendanceDate(memberId, yesterday);
    }

    public Grass findDayGrassByMemberId(Long memberId) {
        LocalDate today = LocalDate.now(clock);
        LocalDate yesterday = today.minusDays(1);

        return grassRepository.findTopByMemberIdOrderByCreatedAtDesc(memberId)
                .filter(grass -> {
                    LocalDate grassCreatedDate = grass.getCreatedAt().toLocalDate();
                    return grassCreatedDate.equals(today) || grassCreatedDate.equals(yesterday);
                })
                .orElse(null);
    }

    public Long getTotalGrassCount(Long memberId) {
        return grassRepository.countByMemberId(memberId);
    }

    public List<Grass> findYearlyGrassByMemberId(Member member, int year) {
        return grassRepository.findByMemberAndYear(member, year);
    }

    public List<Grass> findMonthlyGrassByMemberId(Member member, int year, int month) {
        return grassRepository.findByMemberAndYearAndMonth(member, year, month);
    }

    public StudyTimeResponse getStudyRecord(Member member) {
        Optional<Grass> optionalGrass = findTodayGrassByMemberId(member.getId());
        if (optionalGrass.isPresent()) {
            Grass grass = optionalGrass.get();
            String todayStudyTime = DurationUtils.formatDuration(grass.getStudyTime());
            String totalStudyTime = DurationUtils.formatDuration(member.getStudyRecord().getTotalStudyTime());
            return new StudyTimeResponse(todayStudyTime, totalStudyTime);
        } else {
            String totalStudyTime = DurationUtils.formatDuration(member.getStudyRecord().getTotalStudyTime());
            return new StudyTimeResponse("00:00:00", totalStudyTime);
        }
    }

    @Transactional
    public StudyTimeResponse updateStudyRecord(Member member, StudyTimeRequest request) {
        Optional<Grass> optionalGrass = findTodayGrassByMemberId(member.getId());
        if (optionalGrass.isPresent()) {
            Grass grass = optionalGrass.get();
            Duration todayStudyTime = DurationUtils.parseDuration(request.todayStudyTime());
            grass.updateStudyTime(todayStudyTime);
            grass.updateGrassScore(calculateStudyScore(todayStudyTime));
        } else {
            throw new MissingAttendanceException();
        }
        return getStudyRecord(member);
    }

    public AttendanceResponse getAttendance(Member member) {
        return new AttendanceResponse(isTodayGrassExist(member));
    }
}
