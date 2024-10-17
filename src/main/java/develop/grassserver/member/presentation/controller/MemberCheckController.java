package develop.grassserver.member.presentation.controller;

import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.member.application.service.MemberCheckService;
import develop.grassserver.member.presentation.dto.CheckAuthCodeRequest;
import develop.grassserver.member.presentation.dto.MemberAuthRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버 가입 시 유효성 검사 APIs", description = "멤버 가입 시 유효성 검사를 하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberCheckController {

    private final MemberCheckService memberCheckService;

    @Operation(summary = "이름 중복 검사 API", description = "멤버 이름 중복 검사 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이름 중복 검사 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "400", description = "멤버 이름 형식 오류"),
            @ApiResponse(responseCode = "409", description = "멤버 이름 중복 오류")
    })
    @GetMapping("/check/name")
    public ResponseEntity<ApiUtils.ApiResult<String>> checkName(@RequestParam String name) {
        memberCheckService.checkMemberName(name);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }

    @Operation(summary = "이메일 중복 검사 API", description = "멤버 이메일 중복 검사 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 중복 검사 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "400", description = "멤버 이메일 형식 오류"),
            @ApiResponse(responseCode = "409", description = "멤버 이메일 중복 오류"),
            @ApiResponse(responseCode = "500", description = "인증코드 메일 전송 실패")
    })
    @GetMapping("/check/email")
    public ResponseEntity<ApiUtils.ApiResult<String>> checkEmail(@RequestParam String email) {
        memberCheckService.checkMemberEmail(email);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }

    @Operation(summary = "인증코드 일치 검사 API", description = "인증코드 일치 검사 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증코드 검사 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "400", description = "인증코드 불일치 오류"),
            @ApiResponse(responseCode = "404", description = "인증코드 만료 오류")
    })
    @PostMapping("/check/code")
    public ResponseEntity<ApiUtils.ApiResult<String>> checkCode(@Valid @RequestBody CheckAuthCodeRequest request) {
        memberCheckService.checkAuthCode(request);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
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
        memberCheckService.authMember(request);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }
}
