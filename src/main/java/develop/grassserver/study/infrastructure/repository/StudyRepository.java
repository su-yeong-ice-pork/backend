package develop.grassserver.study.infrastructure.repository;

import develop.grassserver.study.domain.entity.Study;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudyRepository extends JpaRepository<Study, Long> {
    boolean existsByInviteCode(String inviteCode);

    @Query("SELECT DISTINCT s FROM Study s " +
            "JOIN s.members smFilter " +
            "JOIN FETCH s.members sm " +
            "WHERE s.status = TRUE AND smFilter.status = TRUE AND smFilter.member.id = :memberId")
    List<Study> findStudiesByMemberId(@Param("memberId") Long memberId);

    Optional<Study> findStudyByInviteCode(String inviteCode);

    @Query("SELECT s FROM Study s JOIN FETCH s.members WHERE s.id = :studyId AND s.status = TRUE")
    Optional<Study> findStudyByIdWithMembers(@Param("studyId") Long studyId);
}
