package develop.grassserver.grass.infrastructure.repositiory;

import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GrassScoreAggregateQueryRepository {

    private static final String MEMBER_TOTAL_GRASS_SCORE_UPDATE_QUERY = "UPDATE grass_score_aggregate gsa " +
            "JOIN grass g ON gsa.member_id = g.member_id " +
            "SET gsa.grass_score = gsa.grass_score + g.grass_score, " +
            "    gsa.updated_at = NOW() " +
            "WHERE g.attendance_date = :yesterday " +
            "  AND g.status = true";

    private static final String MEMBER_TOTAL_GRASS_SCORE_SELECT_QUERY =
            "SELECT gsa FROM GrassScoreAggregate gsa WHERE gsa.member.status = TRUE ORDER BY gsa.grassScore DESC";

    private final EntityManager entityManager;

    @Transactional
    public void update(LocalDate date) {
        entityManager.createNativeQuery(MEMBER_TOTAL_GRASS_SCORE_UPDATE_QUERY)
                .setParameter("yesterday", date)
                .executeUpdate();
    }

    public List<GrassScoreAggregate> getTopMembers() {
        return entityManager.createQuery(MEMBER_TOTAL_GRASS_SCORE_SELECT_QUERY, GrassScoreAggregate.class)
                .setMaxResults(50)
                .getResultList();
    }
}
