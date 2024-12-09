package develop.grassserver.grass.infrastructure.repositiory;

import develop.grassserver.grass.domain.entity.Grass;
import develop.grassserver.member.domain.entity.Member;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GrassRepository extends JpaRepository<Grass, Long> {
  
    Optional<Grass> findByMemberIdAndAttendanceDate(Long memberId, LocalDate attendanceDate);

    Optional<Grass> findTopByMemberIdOrderByAttendanceDateDesc(Long memberId);

    List<Grass> findByMemberAndAttendanceDateBetween(Member member, LocalDate startDate, LocalDate endDate);

    long countByMemberId(Long memberId);

    @Query("SELECT g "
            + "FROM Grass g JOIN FETCH g.member "
            + "WHERE (g.member.id IN :ids) "
            + "AND (g.createdAt >= :startOfDay AND g.createdAt < :endOfDay)")
    List<Grass> findAllByMemberIdsAndDate(@Param("ids") List<Long> ids,
                                          @Param("startOfDay") LocalDateTime startOfDay,
                                          @Param("endOfDay") LocalDateTime endOfDay);
}
