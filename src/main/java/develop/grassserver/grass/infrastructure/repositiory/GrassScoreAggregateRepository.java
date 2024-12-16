package develop.grassserver.grass.infrastructure.repositiory;

import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.member.domain.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrassScoreAggregateRepository extends JpaRepository<GrassScoreAggregate, Long> {

    boolean existsByMember(Member member);

    @Query("SELECT gsa FROM GrassScoreAggregate gsa WHERE gsa.id IN :ids")
    List<GrassScoreAggregate> findAllByIds(@Param("ids") List<Long> ids);
}
