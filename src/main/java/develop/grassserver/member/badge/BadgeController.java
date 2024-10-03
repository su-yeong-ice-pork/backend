package develop.grassserver.member.badge;

import develop.grassserver.member.badge.dto.FindAllMemberBadgesResponse;
import develop.grassserver.member.security.CustomUserDetails;
import develop.grassserver.utils.ApiUtils;
import develop.grassserver.utils.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "뱃지 CRUD APIs", description = "뱃지 조회, 생성, 수정, 삭제를 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/{id}/badges")
public class BadgeController {

    private final BadgeService badgeService;

    @Operation(summary = "뱃지 조회 API", description = "멤버 뱃지 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "뱃지 조회 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패")
    })
    @GetMapping
    public ResponseEntity<ApiResult<?>> findBadges(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        FindAllMemberBadgesResponse response = badgeService.findAllMemberBadges(id);
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }
}
