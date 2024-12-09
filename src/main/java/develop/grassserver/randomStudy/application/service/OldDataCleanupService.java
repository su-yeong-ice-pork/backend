package develop.grassserver.randomStudy.application.service;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OldDataCleanupService {

    private final EntityManager entityManager;

    public int softDeleteOldRandomStudyApplications(LocalDate targetDate) {
        return entityManager.createQuery(
                        "UPDATE RandomStudyApplication ra SET ra.status = false WHERE ra.attendanceDate = :targetDate AND ra.status = true"
                )
                .setParameter("targetDate", targetDate)
                .executeUpdate();
    }
}
