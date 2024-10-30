package develop.grassserver.grass.presentation.dto;

public record MonthlyGrassResponse(
        Long id,
        int day,
        long studyHour,
        int grassScore
) {
}
