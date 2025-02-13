package develop.grassserver.rank.presentation.dto;

import develop.grassserver.common.utils.DateTimeUtils;
import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.rank.application.dto.StudyRankingData;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

public record StudyRankingResponse(String date, List<StudyRank> ranking) {

    public static StudyRankingResponse from(List<StudyRankingData> dataList) {
        List<StudyRank> ranks = IntStream.range(0, dataList.size())
                .mapToObj(i -> new StudyRank(
                        i + 1,
                        dataList.get(i).studyName(),
                        dataList.get(i).memberCount().intValue(),
                        DurationUtils.formatHourAndMinute(dataList.get(i).totalStudyTime())
                ))
                .toList();

        String date = DateTimeUtils.formatNotificationDate(LocalDateTime.now().minusDays(1));
        return new StudyRankingResponse(date, ranks);
    }

    public record StudyRank(
            int rank,
            String studyName,
            int memberCount,
            String totalStudyTime
    ) {
    }
}
