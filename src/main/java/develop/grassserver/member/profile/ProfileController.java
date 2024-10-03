package develop.grassserver.member.profile;

import develop.grassserver.member.security.CustomUserDetails;
import develop.grassserver.utils.ApiUtils;
import develop.grassserver.utils.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/{id}")
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "프로필 배너 수정 API", description = "프로필 배너 수정 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 배너 수정 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패"),
            @ApiResponse(responseCode = "500", description = "프로필 배너 업로드 실패")
    })
    @PatchMapping("/profile-banner")
    public ResponseEntity<ApiResult<String>> uploadBanner(
            MultipartFile bannerImage,
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        profileService.saveBannerImage(bannerImage, id);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }
}
