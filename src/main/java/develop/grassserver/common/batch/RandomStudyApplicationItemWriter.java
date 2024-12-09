package develop.grassserver.common.batch;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.randomStudy.domain.entity.RandomStudy;
import develop.grassserver.randomStudy.domain.entity.RandomStudyApplication;
import develop.grassserver.randomStudy.domain.entity.RandomStudyMember;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyMemberRepository;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;

public class RandomStudyApplicationItemWriter implements ItemWriter<RandomStudyApplication>, ItemStream {

    private final RandomStudyRepository randomStudyRepository;
    private final RandomStudyMemberRepository randomStudyMemberRepository;

    private LocalTime currentAttendanceTime = null;
    private List<Member> currentGroup = new ArrayList<>();

    public RandomStudyApplicationItemWriter(RandomStudyRepository randomStudyRepository,
                                            RandomStudyMemberRepository randomStudyMemberRepository) {
        this.randomStudyRepository = randomStudyRepository;
        this.randomStudyMemberRepository = randomStudyMemberRepository;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey("currentGroup")) {
            currentGroup = (List<Member>) executionContext.get("currentGroup");
        }
        if (executionContext.containsKey("currentAttendanceTime")) {
            currentAttendanceTime = (LocalTime) executionContext.get("currentAttendanceTime");
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("currentGroup", currentGroup);
        executionContext.put("currentAttendanceTime", currentAttendanceTime);
    }

    @Override
    public void close() throws ItemStreamException {
        createRandomStudy();
        currentGroup.clear();
    }

    @Override
    public void write(Chunk<? extends RandomStudyApplication> chunk) throws Exception {
        for (RandomStudyApplication application : chunk.getItems()) {
            LocalTime attendanceTime = application.getAttendanceTime();

            if (currentAttendanceTime == null || !attendanceTime.equals(currentAttendanceTime)) {
                createRandomStudy();
                currentAttendanceTime = attendanceTime;
            }

            currentGroup.add(application.getMember());

            if (currentGroup.size() >= 20) {
                createRandomStudy();
            }
        }
    }

    private void createRandomStudy() {
        if (currentGroup.isEmpty()) {
            return;
        }

        Collections.shuffle(currentGroup);
        for (int i = 0; i < currentGroup.size(); i += 5) {
            List<Member> group = currentGroup.subList(i, Math.min(i + 5, currentGroup.size()));

            RandomStudy randomStudy = RandomStudy.builder()
                    .name("랜덤 스터디 " + UUID.randomUUID())
                    .attendanceTime(LocalDateTime.of(LocalDate.now(), currentAttendanceTime))
                    .build();
            randomStudyRepository.save(randomStudy);

            List<RandomStudyMember> studyMembers = group.stream()
                    .map(member -> RandomStudyMember.builder()
                            .member(member)
                            .randomStudy(randomStudy)
                            .build())
                    .collect(Collectors.toUnmodifiableList());
            randomStudyMemberRepository.saveAll(studyMembers);
        }

        currentGroup.clear();
    }
}
