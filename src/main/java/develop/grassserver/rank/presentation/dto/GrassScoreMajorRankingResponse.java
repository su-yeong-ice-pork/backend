package develop.grassserver.rank.presentation.dto;

import develop.grassserver.common.utils.DateTimeUtils;
import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.member.domain.entity.Member;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public record GrassScoreMajorRankingResponse(String date, List<MajorRank> ranking) {

    public static GrassScoreMajorRankingResponse from(
            Map<String, List<Member>> membersGroupByMajor,
            Map<Long, GrassScoreAggregate> aggregateMap
    ) {
        List<MajorRank> majorRanks = calculateRanks(membersGroupByMajor, aggregateMap);

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String date = DateTimeUtils.formatRankingDate(yesterday);
        return new GrassScoreMajorRankingResponse(date, majorRanks);
    }

    private static List<MajorRank> calculateRanks(
            Map<String, List<Member>> membersGroupByMajor,
            Map<Long, GrassScoreAggregate> aggregateMap
    ) {
        List<MajorRank> sortedRanks = membersGroupByMajor.entrySet().stream()
                .map(group -> toMajorRank(group, aggregateMap))
                .sorted((r1, r2) -> Integer.compare(r2.majorGrassScore(), r1.majorGrassScore()))
                .collect(Collectors.toList());

        for (int i = 0; i < sortedRanks.size(); i++) {
            MajorRank majorRank = sortedRanks.get(i).withRank(i + 1);
            sortedRanks.set(i, majorRank);
        }
        return sortedRanks;
    }

    private static MajorRank toMajorRank(
            Map.Entry<String, List<Member>> group,
            Map<Long, GrassScoreAggregate> aggregateMap
    ) {
        String majorName = group.getKey();
        int memberCount = group.getValue().size();
        String totalStudyTime = String.format("%d시간", getMajorTotalStudyTime(group));
        int totalGrassScore = calculateTotalGrassScore(group, aggregateMap);
        return new MajorRank(0, majorName, memberCount, totalStudyTime, totalGrassScore);
    }

    private static long getMajorTotalStudyTime(Entry<String, List<Member>> group) {
        return group.getValue().stream()
                .mapToLong(member ->
                        DurationUtils.formatHourDuration(member.getStudyRecord().getTotalStudyTime())
                )
                .sum();
    }

    private static int calculateTotalGrassScore(Entry<String, List<Member>> group, Map<Long, GrassScoreAggregate> aggregateMap) {
        return group.getValue().stream()
                .filter(member -> aggregateMap.containsKey(member.getId()))
                .mapToInt(member -> aggregateMap.get(member.getId()).getGrassScore())
                .sum();
    }

    public record MajorRank(
            int rank,
            String majorName,
            int memberCount,
            String majorTotalStudyTime,
            int majorGrassScore
    ) {

        public MajorRank withRank(int rank) {
            return new MajorRank(rank, this.majorName, this.memberCount, this.majorTotalStudyTime, this.majorGrassScore);
        }
    }
}
