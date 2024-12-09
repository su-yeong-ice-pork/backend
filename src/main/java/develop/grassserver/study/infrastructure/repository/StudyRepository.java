package develop.grassserver.study.infrastructure.repository;

import develop.grassserver.study.domain.entity.Study;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyRepository extends JpaRepository<Study, Long> {
    boolean existsByInviteCode(String inviteCode);

    @Query("SELECT s FROM Study s " +
            "JOIN s.members sm " +
            "WHERE sm.status = TRUE AND s.status = TRUE AND sm.member.id = :memberId")
    List<Study> findStudiesByMemberId(@Param("memberId") Long memberId);

    Optional<Study> findStudyByInviteCode(String inviteCode);
}
