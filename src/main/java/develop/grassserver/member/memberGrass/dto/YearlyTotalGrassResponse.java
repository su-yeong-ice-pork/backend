package develop.grassserver.member.memberGrass.dto;

public record YearlyGrassResponse(
        Long id,
        int year,
        int month,
        int day,
        String studyTime
) {
}
