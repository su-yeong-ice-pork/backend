package develop.grassserver.member.memberGrass.dto;

import java.util.List;

public record YearlyTotalGrassResponse(
        int year,
        List<YearlyGrassResponse> grass
) {
}
