package develop.grassserver.rank.presentation.dto;

import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.study.domain.entity.Study;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record StudyRankingResponse(List<StudyRank> ranking) {

    public static StudyRankingResponse from(List<Study> studies) {
        List<StudyRank> ranks = IntStream.range(0, studies.size())
                .mapToObj(i -> new StudyRank(
                        i + 1,
                        studies.get(i).getName(),
                        studies.get(i).getMembers().size(),
                        DurationUtils.formatHourDuration(studies.get(i).getTotalStudyTime())
                ))
                .collect(Collectors.toList());

        return new StudyRankingResponse(ranks);
    }

    public record StudyRank(
            int rank,
            String studyName,
            int memberCount,
            Long totalStudyTime
    ) {
    }
}
