package develop.grassserver.grass.presentation.dto;

public record YearlyGrassResponse(
        Long id,
        int month,
        int day,
        long studyHour
) {
}
