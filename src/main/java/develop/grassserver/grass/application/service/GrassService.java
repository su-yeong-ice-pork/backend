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

    private final GrassRepository grassRepository;

    private Optional<Grass> findTodayGrassByMemberId(Long memberId) {
        LocalDate today = LocalDate.now();
        return grassRepository.findByMemberIdAndDate(memberId, today.atStartOfDay(), today.atTime(LocalTime.MAX));
    }

    private Optional<Grass> findYesterdayGrassByMemberId(Long memberId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return grassRepository.findByMemberIdAndDate(memberId, yesterday.atStartOfDay(),
                yesterday.atTime(LocalTime.MAX));
    }

    public boolean isTodayGrassExist(Member member) {
        return findTodayGrassByMemberId(member.getId()).isPresent();
    }

    @Transactional
    public Grass createGrass(Member member) {

        if (isTodayGrassExist(member)) {
            throw new AlreadyCheckedInException();
        }

        int currentStreak = 1;
        Optional<Grass> yesterdayGrass = findYesterdayGrassByMemberId(member.getId());

        if (yesterdayGrass.isPresent()) {
            currentStreak = yesterdayGrass.get().getCurrentStreak() + 1;
        }

        Grass grass = Grass.builder()
                .member(member)
                .currentStreak(currentStreak)
                .build();
        return grassRepository.save(grass);
    }

    public Grass findDayGrassByMemberId(Long memberId) {
        LocalDate today = LocalDate.now();
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
