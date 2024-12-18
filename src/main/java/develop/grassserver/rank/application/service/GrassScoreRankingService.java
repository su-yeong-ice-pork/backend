package develop.grassserver.rank.application.service;

import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.grass.application.dto.MemberStudyInfoDTO;
import develop.grassserver.grass.application.service.MemberStudyInfoService;
import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.grass.infrastructure.repositiory.GrassScoreAggregateRepository;
import develop.grassserver.member.application.service.MemberService;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.rank.presentation.dto.GrassScoreIndividualRankingResponse;
import develop.grassserver.rank.presentation.dto.GrassScoreMajorRankingResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GrassScoreRankingService {

    private final RedisService redisService;
    private final MemberService memberService;
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

    public GrassScoreMajorRankingResponse getMajorRanking() {
        Map<String, List<Member>> membersGroupByMajor = memberService.getMembersGroupByMajor();
        Map<Long, GrassScoreAggregate> aggregateMap = getGrassScoreAggregateMap();
        return GrassScoreMajorRankingResponse.from(membersGroupByMajor, aggregateMap);
    }

    private Map<Long, GrassScoreAggregate> getGrassScoreAggregateMap() {
        return grassScoreAggregateRepository.findAll().stream()
                .collect(
                        Collectors.toMap(
                                grassScoreAggregate -> grassScoreAggregate.getMember().getId(),
                                grassScoreAggregate -> grassScoreAggregate
                        )
                );

    }
}
