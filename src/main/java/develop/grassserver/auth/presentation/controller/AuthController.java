package develop.grassserver.auth.presentation.controller;

import develop.grassserver.auth.application.service.AuthService;
import develop.grassserver.auth.presentation.dto.LoginRequest;
import develop.grassserver.auth.presentation.dto.RefreshTokenDTO;
import develop.grassserver.auth.presentation.dto.TokenDTO;
import develop.grassserver.common.annotation.LoginMember;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.member.domain.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 APIs", description = "로그인, 자동 로그인, 로그아웃을 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인 API", description = "멤버 로그인 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "로그인 실패"),
            @ApiResponse(responseCode = "404", description = "해당 멤버를 찾을 수 없음")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResult<TokenDTO>> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenDTO token = authService.login(loginRequest);
        return ResponseEntity.ok()
                .header("Authorization", token.accessToken())
                .body(ApiUtils.success(token));
    }

    @Operation(summary = "자동 로그인 API", description = "멤버 자동 로그인 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "자동 로그인 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "자동 로그인 실패, 재로그인 필요"),
            @ApiResponse(responseCode = "404", description = "해당 멤버를 찾을 수 없음")
    })
    @PostMapping("/auto-login")
    public ResponseEntity<ApiResult<String>> autoLogin(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        TokenDTO token = authService.autoLogin(refreshTokenDTO);
        return ResponseEntity.ok()
                .header("Authorization", token.accessToken())
                .body(ApiUtils.success());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResult<String>> logout(@LoginMember Member member) {
        // 로그인한 사용자 검증 필요
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }
}
