package develop.grassserver.rank.presentation.dto;

import java.util.List;

public record StudyRankingResponse(List<StudyRank> ranking) {

    public record StudyRank(
            int rank,
            String studyName,
            int memberCount,
            Long totalStudyTime
    ) {
    }
}