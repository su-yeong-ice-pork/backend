package develop.grassserver.grass.presentation.dto;

import java.util.List;

public record MonthlyTotalGrassResponse(
        int year,
        int month,
        List<MonthlyGrassResponse> grass
) {
}
