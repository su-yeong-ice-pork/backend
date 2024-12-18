package develop.grassserver.rank.presentation.dto;

import develop.grassserver.study.application.dto.StudyRankingDTO;
import java.util.List;
import java.util.stream.IntStream;

public record StudyRankingResponse(List<StudyRank> ranking) {

    public static StudyRankingResponse from(List<StudyRankingDTO> studyRankingDTOs) {
        List<StudyRank> ranks = IntStream.range(0, studyRankingDTOs.size())
                .mapToObj(i -> {
                    StudyRankingDTO dto = studyRankingDTOs.get(i);
                    return new StudyRank(
                            i + 1,
                            dto.studyName(),
                            dto.memberCount(),
                            dto.totalStudyTime()
                    );
                })
                .toList();
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
