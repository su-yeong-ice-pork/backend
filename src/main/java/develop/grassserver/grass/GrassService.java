package develop.grassserver.grass;

import develop.grassserver.grass.dto.StudyTimeResponse;
import develop.grassserver.grass.exception.MissingAttendanceException;
import develop.grassserver.member.Member;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrassService {
    private final GrassRepository grassRepository;

    public Grass findGrassByMemberId(Long memberId) {
        return grassRepository.findByMemberId(memberId)
                .orElseThrow(MissingAttendanceException::new);
    }

    public StudyTimeResponse getStudyRecord(Member member) {
        Grass grass = findGrassByMemberId(member.getId());
        LocalTime todayStudyTime = grass.getStudyTime();
        LocalTime totalStudyTime = member.getStudyRecord().getTotalStudyTime();
        return new StudyTimeResponse(todayStudyTime, totalStudyTime);
    }
}
