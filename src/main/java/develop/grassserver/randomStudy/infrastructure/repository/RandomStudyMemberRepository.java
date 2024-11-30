package develop.grassserver.randomStudy.infrastructure.repository;

import develop.grassserver.randomStudy.domain.entity.RandomStudyMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomStudyMemberRepository extends JpaRepository<RandomStudyMember, Long> {

    boolean existsByMemberIdAndRandomStudyId(Long memberId, Long randomStudyId);

    List<RandomStudyMember> findAllByRandomStudyId(Long randomStudyId);
}
