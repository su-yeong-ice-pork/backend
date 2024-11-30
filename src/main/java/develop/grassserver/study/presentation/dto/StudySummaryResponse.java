package develop.grassserver.study.presentation.dto;

import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.study.domain.entity.Study;

public record StudySummaryResponse(
        Long id,
        String studyName,
        int memberCount,
        Long totalStudyTime
) {

    public static StudySummaryResponse from(Object[] result) {
        Study study = (Study) result[0];
        Long memberCount = (Long) result[1];
        return new StudySummaryResponse(
                study.getId(),
                study.getName(),
                memberCount.intValue(),
                DurationUtils.formatHourDuration(study.getTotalStudyTime())
        );
    }
}
