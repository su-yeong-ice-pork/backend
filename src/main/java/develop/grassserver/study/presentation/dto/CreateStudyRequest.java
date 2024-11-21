package develop.grassserver.study.presentation.dto;

public record CreateStudyRequest(
        String name,
        String goalMessage,
        long goalTime
) {
}
