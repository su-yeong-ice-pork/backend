package develop.grassserver.rank.application.service;

import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.grass.application.dto.MemberStudyInfoDTO;
import develop.grassserver.grass.application.service.MemberStudyInfoService;
import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.grass.infrastructure.repositiory.GrassScoreAggregateRepository;
import develop.grassserver.rank.presentation.dto.GrassScoreIndividualRankingResponse;
import develop.grassserver.rank.presentation.dto.StudyRankingResponse;
import develop.grassserver.study.application.dto.StudyRankingDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GrassScoreRankingService {

    private final RedisService redisService;
    private final MemberStudyInfoService memberStudyInfoService;

    private final GrassScoreAggregateRepository grassScoreAggregateRepository;

    public GrassScoreIndividualRankingResponse getIndividualRanking() {
        List<Long> rankingIds = redisService.getIndividualGrassScoreRanking();

        List<GrassScoreAggregate> aggregates = grassScoreAggregateRepository.findAllByIds(rankingIds);
        List<Long> memberIds = getMemberIds(aggregates);
        MemberStudyInfoDTO memberStudyInfoDTO = memberStudyInfoService.getMemberStudyInfo(memberIds);

        return GrassScoreIndividualRankingResponse.from(rankingIds, aggregates, memberStudyInfoDTO);
    }

    private List<Long> getMemberIds(List<GrassScoreAggregate> grassScoreAggregates) {
        return grassScoreAggregates.stream()
                .mapToLong(grassScoreAggregate -> grassScoreAggregate.getMember().getId())
                .boxed()
                .toList();
    }

    public StudyRankingResponse getStudyRanking() {
        List<StudyRankingDTO> studyRankingDTOS = redisService.getStudyRanking();
        return StudyRankingResponse.from(studyRankingDTOS);
    }
}
