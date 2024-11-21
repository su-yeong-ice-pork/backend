package develop.grassserver.study.infrastructure.repository;

import develop.grassserver.study.domain.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
    boolean existsByInviteCode(String inviteCode);
}
