package develop.grassserver.grass;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrassRepository extends JpaRepository<Grass, Long> {
    Optional<Grass> findByMemberIdAndCreatedAt(Long memberId, LocalDate createdAt);

}
