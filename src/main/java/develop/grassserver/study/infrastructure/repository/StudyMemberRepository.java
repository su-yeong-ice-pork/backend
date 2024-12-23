package develop.grassserver.study.infrastructure.repository;

import develop.grassserver.study.domain.entity.StudyMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    boolean existsByMemberIdAndStudyId(Long memberId, Long studyId);

    List<StudyMember> findAllByStudyId(Long studyId);
}
