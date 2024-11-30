package develop.grassserver.grass.application.service;

import static develop.grassserver.common.utils.GrassScoreUtil.calculateStudyScore;

import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.grass.application.exception.AlreadyCheckedInException;
import develop.grassserver.grass.application.exception.MissingAttendanceException;
import develop.grassserver.grass.domain.entity.Grass;
import develop.grassserver.grass.infrastructure.repositiory.GrassRepository;
import develop.grassserver.grass.presentation.dto.AttendanceResponse;
import develop.grassserver.grass.presentation.dto.StudyTimeRequest;
import develop.grassserver.grass.presentation.dto.StudyTimeResponse;
import develop.grassserver.member.domain.entity.Member;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrassService {

    private final RedisService redisService;
    private final GrassRepository grassRepository;

    public boolean isTodayGrassExist(Member member) {
        return findTodayGrass(member.getId()).isPresent();
    }

    private Optional<Grass> findTodayGrass(Long memberId) {
        LocalDate today = LocalDate.now();
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
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return grassRepository.findByMemberIdAndAttendanceDate(memberId, yesterday);
    }

    public Grass findDayGrassByMemberId(Long memberId) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        return grassRepository.findTopByMemberIdOrderByAttendanceDateDesc(memberId)
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
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return grassRepository.findByMemberAndAttendanceDateBetween(member, startDate, endDate);
    }

    public List<Grass> findMonthlyGrassByMemberId(Member member, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return grassRepository.findByMemberAndAttendanceDateBetween(member, startDate, endDate);
    }

    public StudyTimeResponse getStudyRecord(Member member) {
        Optional<Grass> optionalGrass = findTodayGrass(member.getId());
        String todayStudyTime = optionalGrass
                .map(grass -> DurationUtils.formatDuration(grass.getStudyTime()))
                .orElse("00:00:00");

        String totalStudyTime = DurationUtils.formatDuration(member.getStudyRecord().getTotalStudyTime());

        return new StudyTimeResponse(todayStudyTime, totalStudyTime);
    }

    @Transactional
    public StudyTimeResponse updateStudyRecord(Member member, StudyTimeRequest request) {
        Grass grass = findTodayGrass(member.getId()).orElseThrow(MissingAttendanceException::new);

        Duration todayStudyTime = DurationUtils.parseDuration(request.todayStudyTime());
        grass.updateStudyTime(todayStudyTime);
        grass.updateGrassScore(calculateStudyScore(todayStudyTime));

        redisService.deleteMemberStudyStatus(member.getId());
        return getStudyRecord(member);
    }

    public AttendanceResponse getAttendance(Member member) {
        return new AttendanceResponse(isTodayGrassExist(member));
    }

    public Map<Long, String> getFriendsTodayStudyTime(List<Long> memberIds) {
        LocalDate today = LocalDate.now();

        Map<Long, String> studyTimes = memberIds.stream()
                .collect(Collectors.toMap(memberId -> memberId, memberId -> "00시간 00분"));

        List<Grass> grass = getAllByMemberIdsAndDate(memberIds, today);

        mappedTodayStudyTime(grass, studyTimes);
        return studyTimes;
    }

    private List<Grass> getAllByMemberIdsAndDate(List<Long> memberIds, LocalDate today) {
        return grassRepository.findAllByMemberIdsAndDate(
                memberIds,
                today.atStartOfDay(),
                today.atTime(LocalTime.MAX)
        );
    }

    private void mappedTodayStudyTime(List<Grass> grass, Map<Long, String> studyTimes) {
        grass.forEach(
                todayGrass ->
                        studyTimes.put(
                                todayGrass.getMember().getId(),
                                DurationUtils.formatHourAndMinute(todayGrass.getStudyTime())
                        )
        );
    }
}
