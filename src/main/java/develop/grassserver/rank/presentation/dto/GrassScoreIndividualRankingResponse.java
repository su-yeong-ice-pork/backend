package develop.grassserver.rank.presentation.dto;

import develop.grassserver.grass.application.dto.MemberStudyInfoDTO;
import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record GrassScoreIndividualRankingResponse(List<IndividualRank> ranking) {

    public static GrassScoreIndividualRankingResponse from(
            List<Long> rankingIds,
            List<GrassScoreAggregate> grassScoreAggregates,
            MemberStudyInfoDTO memberStudyInfoDTO
    ) {
        return new GrassScoreIndividualRankingResponse(

        )
    }

    private List<IndividualRank> get(
            List<Long> rankingIds,
            List<GrassScoreAggregate> grassScoreAggregates,
            MemberStudyInfoDTO memberStudyInfoDTO
    ) {
        List<IndividualRank> ranks = new ArrayList<>();
        int i = 1;
        for (Long rankingId : rankingIds) {
            memberStudyInfoDTO.members().stream()
                    .filter(member ->
                            Objects.equals(member.getId(), grassScoreAggregates.stream()
                                    .filter(grassScoreAggregate ->
                                            Objects.equals(grassScoreAggregate.getId(), rankingId)
                                    )
                                    .findFirst()
                                    .orElseThrow(() -> new EntityNotFoundException("버그")).getId())
                    )
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("버그"));
            new IndividualRank(i, )
        }
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
