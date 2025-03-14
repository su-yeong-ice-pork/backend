package develop.grassserver.grass.infrastructure.repositiory;

import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.member.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrassScoreAggregateRepository
        extends JpaRepository<GrassScoreAggregate, Long>, GrassScoreAggregateCustomRepository {

    boolean existsByMember(Member member);

    Optional<GrassScoreAggregate> findByMember(Member member);
}
