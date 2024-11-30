package develop.grassserver.study.presentation.dto;

import java.util.List;

public record FindAllStudyResponse(
        List<StudySummaryResponse> regularStudies) {
}
