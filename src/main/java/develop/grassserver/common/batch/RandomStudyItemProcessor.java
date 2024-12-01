package develop.grassserver.common.batch;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.randomStudy.domain.entity.RandomStudy;
import develop.grassserver.randomStudy.domain.entity.RandomStudyApplication;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyMemberRepository;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.batch.item.ItemProcessor;

public class RandomStudyItemProcessor implements ItemProcessor<RandomStudyApplication, RandomStudy> {

    private final RandomStudyRepository randomStudyRepository;
    private final RandomStudyMemberRepository randomStudyMemberRepository;
    private LocalTime currentAttendanceTime = null;
    private List<Member> currentGroup = new ArrayList<>();

    public RandomStudyItemProcessor(RandomStudyRepository randomStudyRepository,
                                    RandomStudyMemberRepository randomStudyMemberRepository) {
        this.randomStudyRepository = randomStudyRepository;
        this.randomStudyMemberRepository = randomStudyMemberRepository;
    }

    @Override
    public RandomStudy process(RandomStudyApplication application) throws Exception {
        LocalTime attendanceTime = application.getAttendanceTime();

        if (currentAttendanceTime == null || !attendanceTime.equals(currentAttendanceTime)) {
            createRandomStudy(currentGroup, attendanceTime);
            currentAttendanceTime = attendanceTime;
            currentGroup.clear();
        }

        currentGroup.add(application.getMember());

        if (currentGroup.size() >= 5) {
            RandomStudy study = createRandomStudy(currentGroup, attendanceTime);
            currentGroup.clear();
            return study;
        }

        return null;
    }

    private RandomStudy createRandomStudy(List<Member> members, LocalTime attendanceTime) {
        if (members.isEmpty()) {
            return null;
        }

        RandomStudy randomStudy = RandomStudy.builder()
                .name("랜덤 스터디 " + UUID.randomUUID())
                .attendanceTime(LocalDateTime.of(LocalDate.now(), attendanceTime))
                .build();

        for (Member member : members) {
            randomStudy.addMember(member);
        }

        return randomStudy;
    }
}

