package develop.grassserver.rank.application.service;

import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.grass.application.dto.MemberStudyInfoDTO;
import develop.grassserver.grass.application.service.GrassScoreAggregateService;
import develop.grassserver.grass.application.service.MemberStudyInfoService;
import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.grass.infrastructure.repositiory.GrassScoreAggregateRepository;
import develop.grassserver.rank.presentation.dto.GrassScoreIndividualRankingResponse;
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
    private final GrassScoreAggregateService grassScoreAggregateService;

    private final GrassScoreAggregateRepository grassScoreAggregateRepository;

    public GrassScoreIndividualRankingResponse getIndividualRanking() {
        List<Long> rankingIds = redisService.getIndividualGrassScoreRanking();
        List<GrassScoreAggregate> grassScoreAggregates = grassScoreAggregateRepository.findAllByIds(rankingIds);

        // 1. <Rank, Score> Map -> DTO 파라미터 넘김
        // 2. Ids, grassScoreAggregates 넘겨서 하드 코딩

        List<Long> memberIds = getMemberIds(grassScoreAggregates);

        MemberStudyInfoDTO memberStudyInfo = memberStudyInfoService.getMemberStudyInfo(
                getMemberIds(grassScoreAggregates)
        );



        // 1. 잔디 점수 집계를 가져옴(rankingIds)
        // (2) GrassScoreAggregate + Member join Profile -> SELECT 2회, 로직 재사용
        return null;
    }

    private List<Long> getMemberIds(List<GrassScoreAggregate> grassScoreAggregates) {
        return grassScoreAggregates.stream()
                .mapToLong(grassScoreAggregate -> grassScoreAggregate.getMember().getId())
                .boxed()
                .toList();
    }


}
