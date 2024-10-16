package develop.grassserver.member.memberGrass;

import develop.grassserver.member.Member;
import develop.grassserver.member.memberGrass.dto.MemberTotalStreakResponse;
import develop.grassserver.utils.ApiUtils;
import develop.grassserver.utils.ApiUtils.ApiResult;
import develop.grassserver.utils.annotation.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버 스트릭 관련 조회 APIs", description = "스트릭, 연간 잔디 등을 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/my-page")
public class MemberPageController {
    private final MemberGrassService memberGrassService;

    @Operation(summary = "마이페이지 전체 스트릭 조회 API", description = "앱 사용 일수, 총 공부시간 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패, 재로그인 필요"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    @GetMapping("/record")
    public ResponseEntity<ApiResult<MemberTotalStreakResponse>> getMemberTotalStreak(@LoginMember Member member) {
        return ResponseEntity.ok(ApiUtils.success(memberGrassService.getMemberTotalStreak(member.getId())));
    }
}
