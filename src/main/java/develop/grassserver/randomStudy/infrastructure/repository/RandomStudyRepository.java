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

    @Query("SELECT DISTINCT rs FROM RandomStudy rs " +
            "JOIN rs.members rsmFilter " +
            "JOIN FETCH rs.members rsmFetch " +
            "WHERE rs.status = true " +
            "AND rsmFilter.member.id = :memberId " +
            "AND rs.attendanceTime BETWEEN :startOfDay AND :endOfDay")
    Optional<RandomStudy> findRandomStudyByMemberId(
            @Param("memberId") Long memberId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);
}
