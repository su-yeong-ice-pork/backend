package develop.grassserver.grass.infrastructure.repositiory;

import develop.grassserver.grass.domain.entity.Grass;
import develop.grassserver.member.domain.entity.Member;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GrassRepository extends JpaRepository<Grass, Long> {
    @Query("SELECT g FROM Grass g WHERE g.member.id = :memberId AND g.attendanceDate = :date")
    Optional<Grass> findByMemberIdAndDate(@Param("memberId") Long memberId,
                                          @Param("date") LocalDate date);

    Optional<Grass> findTopByMemberIdOrderByCreatedAtDesc(Long memberId);

    @Query("SELECT g FROM Grass g WHERE g.member = :member AND FUNCTION('YEAR', g.attendanceDate) = :year")
    List<Grass> findByMemberAndYear(@Param("member") Member member, @Param("year") int year);

    @Query("SELECT g FROM Grass g WHERE g.member = :member AND FUNCTION('YEAR', g.attendanceDate) = :year AND FUNCTION('MONTH', g.attendanceDate) = :month")
    List<Grass> findByMemberAndYearAndMonth(@Param("member") Member member, @Param("year") int year,
                                            @Param("month") int month);

    long countByMemberId(Long memberId);
}
