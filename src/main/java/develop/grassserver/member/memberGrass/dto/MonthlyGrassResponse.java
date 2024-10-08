package develop.grassserver.member.memberGrass.dto;

public record MonthlyGrassResponse(
        Long id,
        int day,
        int studyHour,
        int grassScore
) {
}
