package develop.grassserver.member.memberGrass.dto;

public record YearlyGrassResponse(
        Long id,
        int month,
        int day,
        int studyHour
) {
}
