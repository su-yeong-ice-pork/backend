package develop.grassserver.study.application.service;

import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.study.application.dto.StudyRankingDTO;
import develop.grassserver.study.domain.entity.Study;
import develop.grassserver.study.infrastructure.repository.StudyRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyRankingCacheService {

    private final StudyRepository studyRepository;
    private final RedisService redisService;

    @Transactional
    public void calculateStudyRanking() {
        List<Study> studies = studyRepository.findTopByTotalStudyTime();

        List<StudyRankingDTO> rankingDTOs = studies.stream()
                .map(study -> new StudyRankingDTO(
                        study.getName(),
                        study.getMembers().size(),
                        DurationUtils.formatHourDuration(study.getTotalStudyTime())
                ))
                .collect(Collectors.toList());

        redisService.saveStudyRanking(rankingDTOs);
    }
}
