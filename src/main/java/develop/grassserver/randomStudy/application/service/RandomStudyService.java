package develop.grassserver.randomStudy.application.service;

import develop.grassserver.randomStudy.domain.entity.RandomStudy;
import develop.grassserver.randomStudy.infrastructure.repository.RandomStudyRepository;
import develop.grassserver.randomStudy.presentation.dto.RandomStudyDetailResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RandomStudyService {

    private final RandomStudyRepository randomStudyRepository;

    public RandomStudyDetailResponse getRandomStudyDetail(Long studyId) {
        RandomStudy randomStudy = randomStudyRepository.findById(studyId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 스터디를 찾을 수 없습니다."));

        return RandomStudyDetailResponse.from(randomStudy);
    }
}
