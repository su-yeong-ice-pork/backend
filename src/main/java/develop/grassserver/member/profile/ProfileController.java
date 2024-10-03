package develop.grassserver.member.profile;

import develop.grassserver.member.security.CustomUserDetails;
import develop.grassserver.utils.ApiUtils;
import develop.grassserver.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/{id}")
public class ProfileController {

    private final ProfileService profileService;

    @PatchMapping("/profile-banner")
    public ResponseEntity<ApiResult<String>> uploadBanner(
            MultipartFile bannerImage,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        profileService.saveBannerImage(bannerImage, userDetails.getMember());
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }
}
