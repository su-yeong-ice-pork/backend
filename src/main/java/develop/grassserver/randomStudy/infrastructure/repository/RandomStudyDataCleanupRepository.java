package develop.grassserver.randomStudy.infrastructure.repository;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RandomStudyDataCleanupRepository {

    private final EntityManager entityManager;

    @Transactional
    public int deleteOldRandomStudyApplications(LocalDate targetDate) {
        return entityManager.createQuery(
                        "UPDATE RandomStudyApplication ra SET ra.status = false WHERE ra.attendanceDate = :targetDate AND ra.status = true"
                )
                .setParameter("targetDate", targetDate)
                .executeUpdate();
    }

    @Transactional
    public int deleteOldRandomStudies() {
        return entityManager.createQuery(
                        "UPDATE RandomStudy rs SET rs.status = false WHERE rs.status = true"
                )
                .executeUpdate();
    }

    @Transactional
    public int deleteOldRandomStudyMembers() {
        return entityManager.createQuery(
                        "UPDATE RandomStudyMember rm SET rm.status = false WHERE rm.status = true"
                )
                .executeUpdate();
    }
}
