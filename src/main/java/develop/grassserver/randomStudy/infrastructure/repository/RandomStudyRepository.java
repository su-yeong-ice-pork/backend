package develop.grassserver.randomStudy.infrastructure.repository;

import develop.grassserver.randomStudy.domain.entity.RandomStudy;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomStudyRepository extends JpaRepository<RandomStudy, Long> {

    @Query("SELECT rs FROM RandomStudy rs " +
            "JOIN rs.members rsm " +
            "WHERE rsm.member.id = :memberId " +
            "AND rs.attendanceTime BETWEEN :startOfDay AND :endOfDay " +
            "AND rs.status = true")
    Optional<RandomStudy> findRandomStudyByMemberId(
            @Param("memberId") Long memberId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);
}
