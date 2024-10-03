package develop.grassserver.grass;

import develop.grassserver.grass.dto.StudyTimeRequest;
import develop.grassserver.grass.dto.StudyTimeResponse;
import develop.grassserver.grass.exception.MissingAttendanceException;
import develop.grassserver.member.Member;
import develop.grassserver.utils.duration.DurationUtils;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrassService {
    private final GrassRepository grassRepository;

    public Grass findTodayGrassByMemberId(Long memberId) {
        return grassRepository.findByMemberIdAndCreatedAt(memberId, LocalDate.now())
                .orElseThrow(MissingAttendanceException::new);
    }

    public StudyTimeResponse getStudyRecord(Member member) {
        Grass grass = findTodayGrassByMemberId(member.getId());
        String todayStudyTime = DurationUtils.formatDuration(grass.getStudyTime());
        String totalStudyTime = DurationUtils.formatDuration(member.getStudyRecord().getTotalStudyTime());
        return new StudyTimeResponse(todayStudyTime, totalStudyTime);
    }

    public void updateStudyRecord(Member member, StudyTimeRequest request) {
        Grass grass = findTodayGrassByMemberId(member.getId());
        grass.updateStudyTime(DurationUtils.parseDuration(request.todayStudyTime()));
        grassRepository.save(grass);
    }
}
