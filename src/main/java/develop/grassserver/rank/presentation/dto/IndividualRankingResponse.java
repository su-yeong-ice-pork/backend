package develop.grassserver.rank.presentation.dto;

import develop.grassserver.common.utils.DateTimeUtils;
import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.member.domain.entity.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record IndividualRankingResponse(String date, List<IndividualRank> ranking) {

    public static IndividualRankingResponse from(List<GrassScoreAggregate> aggregates) {
        List<IndividualRank> individualRanks = new ArrayList<>();
        for (int i = 1; i <= aggregates.size(); i++) {
            GrassScoreAggregate aggregate = aggregates.get(i - 1);
            Member member = aggregate.getMember();
            individualRanks.add(getIndividualRank(i, member, aggregate));
        }
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
