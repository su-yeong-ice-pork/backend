package develop.grassserver.profile.presentation.dto;

import jakarta.validation.constraints.Min;

public record FreezeExchangeQuantityRequest(

        @Min(value = 1, message = "프리즈 교환 요청 개수는 최소 1개입니다.")
        int quantity
) {
}
