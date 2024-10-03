package develop.grassserver.member.memberGrass.dto;

public record MonthlyGrassResponse(
        Long id,
        int day,
        String studyTime,
        int grassScore
) {
}
