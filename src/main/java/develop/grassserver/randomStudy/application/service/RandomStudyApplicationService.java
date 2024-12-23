package develop.grassserver.randomStudy.application.service;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.randomStudy.application.exception.ApplicationDeadlinePassedException;
import develop.grassserver.randomStudy.application.exception.DuplicateApplicationException;
import develop.grassserver.randomStudy.domain.entity.RandomStudyApplication;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyApplicationRepository;
import develop.grassserver.randomStudy.presentation.dto.ApplyRandomStudyRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RandomStudyApplicationService {

    public static final int DEADLINE_HOUR = 5;

    private final RandomStudyApplicationRepository applicationRepository;

    @Transactional
    public void applyRandomStudy(Member member, ApplyRandomStudyRequest request) {
        validateApplicationDeadline();
        checkDuplicateApplication(member, request.attendanceDate());

        RandomStudyApplication application = createRandomStudyApplication(member, request);

        applicationRepository.save(application);
    }

    private RandomStudyApplication createRandomStudyApplication(Member member, ApplyRandomStudyRequest request) {
        return RandomStudyApplication.builder()
                .member(member)
                .attendanceDate(request.attendanceDate())
                .attendanceTime(request.attendanceTime())
                .build();
    }

    private void validateApplicationDeadline() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.withHour(DEADLINE_HOUR);

        if (now.isAfter(deadline)) {
            throw new ApplicationDeadlinePassedException();
        }
    }

    private void checkDuplicateApplication(Member member, LocalDate attendanceDate) {
        boolean isDuplicateApplication = applicationRepository.existsByMemberAndAttendanceDate(member, attendanceDate);
        if (isDuplicateApplication) {
            throw new DuplicateApplicationException();
        }
    }
}
