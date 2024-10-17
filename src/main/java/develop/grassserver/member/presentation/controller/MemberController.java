package develop.grassserver.member.presentation.controller;

import develop.grassserver.auth.application.service.JwtUserService;
import develop.grassserver.auth.presentation.dto.LoginRequest;
import develop.grassserver.auth.presentation.dto.RefreshTokenDTO;
import develop.grassserver.auth.presentation.dto.TokenDTO;
import develop.grassserver.common.annotation.LoginMember;
import develop.grassserver.common.security.CustomUserDetails;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.member.application.service.MemberService;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.member.presentation.dto.ChangePasswordRequest;
import develop.grassserver.member.presentation.dto.MemberAuthRequest;
import develop.grassserver.member.presentation.dto.MemberJoinRequest;
import develop.grassserver.member.presentation.dto.MemberProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버 CRUD APIs", description = "멤버 조회, 생성, 수정, 삭제를 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtUserService jwtUserService;

    @Operation(summary = "내정보 조회 API", description = "내정보 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내정보 조회 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패"),
    })
    @GetMapping
    public ResponseEntity<ApiResult<MemberProfileResponse>> findMember(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        MemberProfileResponse response = memberService.findMemberProfile(userDetails.getMember());
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }

    @Operation(summary = "로그인 API", description = "멤버 로그인 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "로그인 실패"),
            @ApiResponse(responseCode = "404", description = "해당 멤버를 찾을 수 없음")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResult<TokenDTO>> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenDTO token = jwtUserService.login(loginRequest);
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
        TokenDTO token = jwtUserService.autoLogin(refreshTokenDTO);
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

    @Operation(summary = "회원가입 API", description = "멤버 회원가입 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패"),
            @ApiResponse(responseCode = "409", description = "중복 회원 존재")
    })
    @PostMapping
    public ResponseEntity<ApiResult<?>> signUp(@Valid @RequestBody MemberJoinRequest request) {
        memberService.saveMember(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success("회원가입 성공"));
    }

    @Operation(summary = "멤버 인증 API", description = "멤버 인증 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패. 이름과 이메일이 불일치"),
            @ApiResponse(responseCode = "404", description = "정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "인증코드 메일 전송 실패")
    })
    @PostMapping("/auth")
    public ResponseEntity<ApiResult<String>> auth(@Valid @RequestBody MemberAuthRequest request) {
        memberService.authMember(request);
        return ResponseEntity.ok()
                .body(ApiUtils.success("인증 성공"));
    }

    @Operation(summary = "비밀번호 재설정 API", description = "비밀번호 재설정 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패. 이름과 이메일이 불일치"),
            @ApiResponse(responseCode = "404", description = "정보를 찾을 수 없음"),
    })
    @PatchMapping
    public ResponseEntity<ApiResult<String>> updatePassword(@Valid @RequestBody ChangePasswordRequest request) {
        memberService.changeMemberPassword(request);
        return ResponseEntity.ok()
                .body(ApiUtils.success("비밀번호 재설정 성공"));
    }
}
