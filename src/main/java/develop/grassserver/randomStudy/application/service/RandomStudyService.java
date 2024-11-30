package develop.grassserver.randomStudy.application.service;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.randomStudy.application.exception.NotARandomStudyMemberException;
import develop.grassserver.randomStudy.domain.entity.RandomStudy;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyMemberRepository;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyRepository;
import develop.grassserver.randomStudy.presentation.dto.RandomStudyDetailResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RandomStudyService {

    private final RandomStudyRepository randomStudyRepository;
    private final RandomStudyMemberRepository randomStudyMemberRepository;

    public RandomStudyDetailResponse getRandomStudyDetail(Member member, Long studyId) {
        if (!randomStudyMemberRepository.existsByMemberIdAndRandomStudyId(member.getId(), studyId)) {
            throw new NotARandomStudyMemberException();
        }

        RandomStudy randomStudy = randomStudyRepository.findById(studyId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 스터디를 찾을 수 없습니다."));

        return RandomStudyDetailResponse.from(randomStudy);
    }
}
