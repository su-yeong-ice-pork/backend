package develop.grassserver.randomStudy.infrastructure.repository;

import develop.grassserver.member.domain.entity.Member;
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
            "JOIN RandomStudyMember rsm ON rs = rsm.randomStudy " +
            "WHERE rsm.status = TRUE AND rs.status = TRUE AND rsm.member = :member " +
            "AND rs.attendanceTime BETWEEN :startOfDay AND :endOfDay")
    Optional<RandomStudy> findByMemberAndToday(@Param("member") Member member,
                                               @Param("startOfDay") LocalDateTime startOfDay,
                                               @Param("endOfDay") LocalDateTime endOfDay);

}
