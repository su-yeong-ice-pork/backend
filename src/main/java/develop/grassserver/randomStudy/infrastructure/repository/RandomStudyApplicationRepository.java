package develop.grassserver.randomStudy.infrastructure.repository;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.randomStudy.domain.entity.RandomStudyApplication;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomStudyApplicationRepository extends JpaRepository<RandomStudyApplication, Long> {

    boolean existsByMemberAndAttendanceDate(Member member, LocalDate attendanceDate);
}
