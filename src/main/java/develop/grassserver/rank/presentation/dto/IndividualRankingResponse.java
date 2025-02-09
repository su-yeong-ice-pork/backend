package develop.grassserver.rank.presentation.dto;

import develop.grassserver.common.utils.DateTimeUtils;
import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.member.domain.entity.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

public record IndividualRankingResponse(String date, List<IndividualRank> ranking) {

    public static IndividualRankingResponse from(List<GrassScoreAggregate> aggregates) {
        List<IndividualRank> individualRanks = IntStream.range(0, aggregates.size())
                .mapToObj(i -> {
                    GrassScoreAggregate aggregate = aggregates.get(i);
                    Member member = aggregate.getMember();
                    return getIndividualRank(i + 1, member, aggregate); // 순위는 1부터 시작하므로 i+1
                })
                .toList();

        String date = DateTimeUtils.formatNotificationDate(LocalDateTime.now().minusDays(1));
        return new IndividualRankingResponse(date, individualRanks);
    }

    private static IndividualRank getIndividualRank(int i, Member member, GrassScoreAggregate aggregate) {
        return new IndividualRank(
                i,
                member.getId(),
                member.getName(),
                member.getProfile().getImage(),
                DurationUtils.formatHourAndMinute(member.getStudyRecord().getTotalStudyTime()),
                aggregate.getGrassScore()
        );
    }

    public record IndividualRank(
            int rank,
            Long memberId,
            String name,
            String profileImage,
            String totalStudyTime,
            int grassScore
    ) {
    }
}
