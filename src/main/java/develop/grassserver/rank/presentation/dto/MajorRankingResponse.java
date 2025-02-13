package develop.grassserver.rank.presentation.dto;

import develop.grassserver.common.utils.DateTimeUtils;
import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.rank.application.dto.MajorRankingData;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

public record MajorRankingResponse(
        String date,
        List<MajorRank> ranking
) {

    public static MajorRankingResponse from(List<MajorRankingData> dataList) {
        String date = DateTimeUtils.formatNotificationDate(LocalDateTime.now().minusDays(1));
        return new MajorRankingResponse(date, mapToMajorRanks(dataList));
    }

    private static List<MajorRank> mapToMajorRanks(List<MajorRankingData> dataList) {
        return IntStream.range(0, dataList.size())
                .mapToObj(i -> toMajorRank(i, dataList.get(i)))
                .toList();
    }

    private static MajorRank toMajorRank(int i, MajorRankingData data) {
        return new MajorRank(
                i + 1,
                data.majorName(),
                data.memberCount(),
                DurationUtils.formatHourAndMinuteByLongTypeTime(data.majorTotalStudyTime()),
                data.majorGrassScore()
        );
    }

    public record MajorRank(
            int rank,
            String majorName,
            long memberCount,
            String majorTotalStudyTime,
            long majorGrassScore
    ) {
    }
}
