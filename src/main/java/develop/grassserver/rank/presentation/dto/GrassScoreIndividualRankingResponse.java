package develop.grassserver.rank.presentation.dto;

import develop.grassserver.common.utils.DateTimeUtils;
import develop.grassserver.grass.application.dto.MemberStudyInfoDTO;
import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.member.domain.entity.Member;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public record GrassScoreIndividualRankingResponse(String date, List<IndividualRank> ranking) {

    public static GrassScoreIndividualRankingResponse from(
            List<Long> rankingIds,
            List<GrassScoreAggregate> grassScoreAggregates,
            MemberStudyInfoDTO memberStudyInfoDTO
    ) {
        Map<Long, GrassScoreAggregate> aggregateMap = grassScoreAggregates.stream()
                .collect(Collectors.toMap(
                        GrassScoreAggregate::getId,
                        grassScoreAggregate -> grassScoreAggregate
                ));

        Map<Long, Member> rankingMemberMap = grassScoreAggregates.stream()
                .collect(Collectors.toMap(
                        GrassScoreAggregate::getId,
                        GrassScoreAggregate::getMember
                ));

        AtomicInteger rank = new AtomicInteger(1);
        List<IndividualRank> ranks =
                getIndividualRanks(rankingIds, memberStudyInfoDTO, rank, rankingMemberMap, aggregateMap);

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String date = DateTimeUtils.formatRankingDate(yesterday);
        return new GrassScoreIndividualRankingResponse(date, ranks);
    }

    private static List<IndividualRank> getIndividualRanks(
            List<Long> rankingIds,
            MemberStudyInfoDTO memberStudyInfoDTO,
            AtomicInteger rank,
            Map<Long, Member> rankingMemberMap,
            Map<Long, GrassScoreAggregate> aggregateMap
    ) {
        return rankingIds.stream()
                .map(rankingId ->
                        createIndividualRank(
                                rank,
                                memberStudyInfoDTO,
                                rankingId,
                                rankingMemberMap,
                                aggregateMap
                        )
                )
                .toList();
    }

    private static IndividualRank createIndividualRank(
            AtomicInteger rank,
            MemberStudyInfoDTO memberStudyInfoDTO,
            Long rankingId,
            Map<Long, Member> rankingMemberMap,
            Map<Long, GrassScoreAggregate> aggregateMap
    ) {
        Member findMember = rankingMemberMap.get(rankingId);
        Long findMemberId = findMember.getId();

        return new IndividualRank(
                rank.getAndIncrement(),
                findMemberId,
                findMember.getName(),
                findMember.getProfile().getImage(),
                memberStudyInfoDTO.studyTimes().get(findMemberId),
                memberStudyInfoDTO.studyStatuses().get(findMemberId),
                aggregateMap.get(rankingId).getGrassScore()
        );
    }

    public record IndividualRank(
            int rank,
            Long memberId,
            String name,
            String profileImage,
            String todayStudyTime,
            boolean studyStatus,
            int grassScore
    ) {
    }
}
