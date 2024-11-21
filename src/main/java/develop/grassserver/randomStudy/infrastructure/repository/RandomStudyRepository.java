package develop.grassserver.randomStudy.infrastructure.repository;

import develop.grassserver.randomStudy.domain.entity.RandomStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomStudyRepository extends JpaRepository<RandomStudy, Long> {
}
