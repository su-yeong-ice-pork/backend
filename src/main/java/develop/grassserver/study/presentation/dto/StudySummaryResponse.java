package develop.grassserver.study.presentation.dto;

import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.study.domain.entity.Study;

public record StudySummaryResponse(
        Long id,
        String studyName,
        int memberCount,
        Long totalStudyTime
) {

    public static StudySummaryResponse from(Study study) {
        return new StudySummaryResponse(
                study.getId(),
                study.getName(),
                study.getMembers().size(),
                DurationUtils.formatHourDuration(study.getTotalStudyTime())
        );
    }
}
