package develop.grassserver.grass.infrastructure.repositiory;

import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.grass.infrastructure.query.GrassScoreQuery;
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
    private final EntityManager entityManager;

    @Transactional
    public void update(LocalDate date) {
        entityManager.createNativeQuery(GrassScoreQuery.MEMBER_TOTAL_GRASS_SCORE_UPDATE_QUERY)
                .setParameter("yesterday", date)
                .executeUpdate();
    }

    @Transactional
    public List<GrassScoreAggregate> getTopMembers() {
        return entityManager.createQuery(GrassScoreQuery.MEMBER_TOTAL_GRASS_SCORE_SELECT_QUERY,
                        GrassScoreAggregate.class)
                .setMaxResults(50)
                .getResultList();
    }
}
