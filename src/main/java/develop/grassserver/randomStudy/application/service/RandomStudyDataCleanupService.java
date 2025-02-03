package develop.grassserver.randomStudy.application.service;

import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyDataCleanupRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RandomStudyDataCleanupService {

    private final RandomStudyDataCleanupRepository randomStudyDataCleanupRepository;

    @Transactional
    public int deleteOldRandomStudyApplications(LocalDate targetDate) {
        return randomStudyDataCleanupRepository.deleteOldRandomStudyApplications(targetDate);
    }

    @Transactional
    public int deleteOldRandomStudies() {
        return randomStudyDataCleanupRepository.deleteOldRandomStudies();
    }

    @Transactional
    public int deleteOldRandomStudyMembers() {
        return randomStudyDataCleanupRepository.deleteOldRandomStudyMembers();
    }
}