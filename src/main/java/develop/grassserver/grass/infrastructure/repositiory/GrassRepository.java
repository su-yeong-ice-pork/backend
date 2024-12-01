package develop.grassserver.grass.infrastructure.repositiory;

import develop.grassserver.grass.domain.entity.Grass;
import develop.grassserver.member.domain.entity.Member;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrassRepository extends JpaRepository<Grass, Long> {

    @Query("SELECT g FROM Grass g WHERE g.member.id = :memberId AND g.createdAt >= :startOfDay AND g.createdAt < :endOfDay")
    Optional<Grass> findByMemberIdAndDate(@Param("memberId") Long memberId,
                                          @Param("startOfDay") LocalDateTime startOfDay,
                                          @Param("endOfDay") LocalDateTime endOfDay);

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
