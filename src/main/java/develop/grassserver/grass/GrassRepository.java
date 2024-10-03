package develop.grassserver.grass;

import develop.grassserver.member.Member;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GrassRepository extends JpaRepository<Grass, Long> {
    Optional<Grass> findByMemberIdAndCreatedAt(Long memberId, LocalDate createdAt);

    @Query("SELECT g FROM Grass g WHERE g.member = :member AND FUNCTION('YEAR', g.createdAt) = :year")
    List<Grass> findByMemberAndYear(@Param("member") Member member, @Param("year") int year);

    @Query("SELECT g FROM Grass g WHERE g.member = :member AND FUNCTION('YEAR', g.createdAt) = :year AND FUNCTION('MONTH', g.createdAt) = :month")
    List<Grass> findByMemberAndYearAndMonth(@Param("member") Member member, @Param("year") int year,
                                            @Param("month") int month);
}
