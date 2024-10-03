package develop.grassserver.member.memberGrass.dto;

import java.util.List;

public record MonthlyTotalGrassResponse(
        int year,
        int month,
        List<MonthlyGrassResponse> grass
) {
}
