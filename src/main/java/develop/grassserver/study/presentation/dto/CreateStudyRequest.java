package develop.grassserver.study.presentation.dto;

import develop.grassserver.study.domain.entity.Study;

public record CreateStudyRequest(
        String name,
        String goalMessage,
        long goalTime
) {

    public static Study from(CreateStudyRequest request) {
        return Study.builder()
                .name(request.name())
                .goalMessage(request.goalMessage())
                .goalTime(request.goalTime())
                .build();

    }
}
