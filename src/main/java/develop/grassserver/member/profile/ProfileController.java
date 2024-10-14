package develop.grassserver.member.profile;

import develop.grassserver.member.profile.dto.FindAllDefaultProfileImagesResponse;
import develop.grassserver.member.profile.dto.FindAllDefaultBannerImagesResponse;
import develop.grassserver.member.profile.dto.UpdateBannerImageRequest;
import develop.grassserver.member.profile.dto.UpdateProfileImageRequest;
import develop.grassserver.utils.ApiUtils;
import develop.grassserver.utils.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/{id}")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile-images")
    public ResponseEntity<ApiResult<FindAllDefaultProfileImagesResponse>> getDefaultProfileImages() {
        FindAllDefaultProfileImagesResponse response = profileService.getDefaultProfileImages();
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }

    @GetMapping("/banner-images")
    public ResponseEntity<ApiResult<?>> getDefaultBannerImages() {
        FindAllDefaultBannerImagesResponse response = profileService.getDefaultBannerImages();
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }

    @Operation(summary = "프로필 배너 수정 API", description = "프로필 배너 수정 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 배너 수정 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패"),
            @ApiResponse(responseCode = "500", description = "프로필 배너 업로드 실패")
    })
    @PatchMapping("/profile-banner")
    public ResponseEntity<ApiResult<String>> uploadBanner(
            MultipartFile bannerImage,
            @PathVariable Long id
    ) {
        profileService.saveBannerImage(bannerImage, id);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }

    @Operation(summary = "프로필 이미지 수정 API", description = "프로필 이미지 수정 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 이미지 수정 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패"),
            @ApiResponse(responseCode = "500", description = "프로필 이미지 업로드 실패")
    })
    @PatchMapping("/profile-image")
    public ResponseEntity<ApiResult<String>> uploadProfileImage(
            MultipartFile profileImage,
            @PathVariable Long id
    ) {
        profileService.saveProfileImage(profileImage, id);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }

    @PatchMapping("/default-image")
    public ResponseEntity<ApiResult<String>> updateProfileImage(
            @PathVariable Long id,
            @RequestBody UpdateProfileImageRequest request
    ) {
        profileService.updateProfileImage(id, request);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }

    @PatchMapping("/default-banner")
    public ResponseEntity<ApiResult<String>> updateBannerImage(
            @PathVariable Long id,
            @RequestBody UpdateBannerImageRequest request
    ) {
        profileService.updateBannerImage(id, request);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }
}
