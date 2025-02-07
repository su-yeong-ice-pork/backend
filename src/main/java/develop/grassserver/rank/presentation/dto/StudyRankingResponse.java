package develop.grassserver.rank.presentation.dto;

import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.rank.application.dto.StudyRankingData;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record StudyRankingResponse(List<StudyRank> ranking) {

    public static StudyRankingResponse from(List<StudyRankingData> dataList) {
        List<StudyRank> ranks = IntStream.range(0, dataList.size())
                .mapToObj(i -> new StudyRank(
                        i + 1,
                        dataList.get(i).studyName(),
                        dataList.get(i).memberCount().intValue(),
                        DurationUtils.formatHourDuration(dataList.get(i).totalStudyTime())
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
