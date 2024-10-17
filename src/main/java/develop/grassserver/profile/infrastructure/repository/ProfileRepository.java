package develop.grassserver.profile.infrastructure.repository;

import develop.grassserver.profile.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
