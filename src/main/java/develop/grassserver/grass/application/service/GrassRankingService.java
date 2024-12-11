package develop.grassserver.grass.application.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GrassRankingService {

    private final EntityManager entityManager;

    public int updateTotalGrassScore() {

        return entityManager.createQuery(
                "UPDATE GrassScoreAggregate gsa SET "
        )
    }
}
