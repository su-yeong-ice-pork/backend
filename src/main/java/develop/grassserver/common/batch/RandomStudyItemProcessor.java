package develop.grassserver.common.batch;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.randomStudy.domain.entity.RandomStudy;
import develop.grassserver.randomStudy.domain.entity.RandomStudyApplication;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemProcessor;

public class RandomStudyItemProcessor implements ItemProcessor<RandomStudyApplication, List<RandomStudy>> {

    private LocalTime currentAttendanceTime = null;
    private List<Member> currentGroup = new ArrayList<>();

    @Override
    public List<RandomStudy> process(RandomStudyApplication application) throws Exception {
        LocalTime attendanceTime = application.getAttendanceTime();

        if (currentAttendanceTime == null) {
            currentAttendanceTime = attendanceTime;
        }

        if (!attendanceTime.equals(currentAttendanceTime)) {
            List<RandomStudy> studies = createRandomStudy();
            currentAttendanceTime = attendanceTime;
            currentGroup.add(application.getMember());
            return studies;
        }

        currentGroup.add(application.getMember());

        return new ArrayList<>();
    }

    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        List<RandomStudy> studies = createRandomStudy();
        return ExitStatus.COMPLETED;
    }

    private List<RandomStudy> createRandomStudy() {
        if (currentGroup.isEmpty()) {
            return new ArrayList<>();
        }

        Collections.shuffle(currentGroup);
        List<RandomStudy> result = new ArrayList<>();
        for (int i = 0; i < currentGroup.size(); i += 5) {
            List<Member> group = currentGroup.subList(i, Math.min(i + 5, currentGroup.size()));

            RandomStudy randomStudy = RandomStudy.builder()
                    .name("랜덤 스터디 " + UUID.randomUUID())
                    .attendanceTime(LocalDateTime.of(LocalDate.now(), currentAttendanceTime))
                    .build();

            for (Member member : group) {
                randomStudy.addMember(member);
            }
            result.add(randomStudy);
        }

        currentGroup.clear();
        return result;
    }
}

