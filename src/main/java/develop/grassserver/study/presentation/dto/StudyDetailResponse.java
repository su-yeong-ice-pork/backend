package develop.grassserver.study.presentation.dto;

import static develop.grassserver.common.utils.duration.DurationUtils.formatHourDuration;

import develop.grassserver.study.domain.entity.Study;

public record StudyDetailResponse(
        Long id,
        String studyName,
        String goalMessage,
        Long goalTime,
        Long totalStudyTime,
        String inviteCode
) {
    public static StudyDetailResponse from(Study study) {
        return new StudyDetailResponse(
                study.getId(),
                study.getName(),
                study.getGoalMessage(),
                study.getGoalTime(),
                formatHourDuration(study.getTotalStudyTime()),
                study.getInviteCode()
        );
    }
}
