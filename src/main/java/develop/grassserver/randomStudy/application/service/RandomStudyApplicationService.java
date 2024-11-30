package develop.grassserver.randomStudy.application.service;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.randomStudy.domain.entity.RandomStudyApplication;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyApplicationRepository;
import develop.grassserver.randomStudy.presentation.dto.ApplyRandomStudyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RandomStudyApplicationService {
    private final RandomStudyApplicationRepository applicationRepository;

    public void applyRandomStudy(Member member, ApplyRandomStudyRequest request) {
        RandomStudyApplication application = RandomStudyApplication.builder()
                .member(member)
                .attendanceDate(request.attendanceDate())
                .attendanceTime(request.attendanceTime())
                .build();

        applicationRepository.save(application);
    }
}
