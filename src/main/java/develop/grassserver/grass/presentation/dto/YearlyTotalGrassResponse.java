package develop.grassserver.grass.presentation.dto;

import java.util.List;

public record YearlyTotalGrassResponse(
        int year,
        List<YearlyGrassResponse> grass
) {
}
