package develop.grassserver.grass;

import develop.grassserver.grass.dto.AttandanceResponse;
import develop.grassserver.grass.dto.StudyTimeRequest;
import develop.grassserver.grass.dto.StudyTimeResponse;
import develop.grassserver.grass.exception.MissingAttendanceException;
import develop.grassserver.member.Member;
import develop.grassserver.utils.duration.DurationUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GrassService {
    private final GrassRepository grassRepository;

    public Optional<Grass> findTodayGrassByMemberId(Long memberId) {
        LocalDate today = LocalDate.now();
        return grassRepository.findByMemberIdAndDate(memberId, today.atStartOfDay(), today.atTime(LocalTime.MAX));
    }

    public Grass findDayGrassByMemberId(Long memberId) {
        LocalDate today = LocalDate.now();

        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.atTime(LocalTime.MAX);
        LocalDateTime startOfYesterday = startOfToday.minusDays(1);
        LocalDateTime endOfYesterday = endOfToday.minusDays(1);

        return Stream.of(
                        grassRepository.findByMemberIdAndDate(memberId, startOfToday, endOfToday),
                        grassRepository.findByMemberIdAndDate(memberId, startOfYesterday, endOfYesterday)
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElse(null);
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
    public void updateStudyRecord(Member member, StudyTimeRequest request) {
        Optional<Grass> optionalGrass = findTodayGrassByMemberId(member.getId());
        if (optionalGrass.isPresent()) {
            Grass grass = optionalGrass.get();
            grass.updateStudyTime(DurationUtils.parseDuration(request.todayStudyTime()));
        } else {
            throw new MissingAttendanceException();
        }
    }

    public AttandanceResponse getAttendance(Member member) {
        return new AttandanceResponse(findTodayGrassByMemberId(member.getId()).isPresent());
    }
}
