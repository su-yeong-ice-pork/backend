package develop.grassserver.study.infrastructure.repository;

import develop.grassserver.study.domain.entity.Study;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyRepository extends JpaRepository<Study, Long> {
    boolean existsByInviteCode(String inviteCode);

    @Query("SELECT sm.study FROM StudyMember sm WHERE sm.member.id = :memberId AND sm.status = TRUE")
    List<Study> findAllStudiesByMemberId(@Param("memberId") Long memberId);
}
