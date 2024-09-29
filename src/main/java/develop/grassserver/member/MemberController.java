package develop.grassserver.member;

import develop.grassserver.member.dto.MemberJoinRequest;
import develop.grassserver.member.dto.MemberJoinSuccessResponse;
import develop.grassserver.member.login.JwtUserService;
import develop.grassserver.member.login.LoginRequest;
import develop.grassserver.utils.ApiUtils;
import develop.grassserver.utils.ApiUtils.ApiResult;
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

@Tag(name = "멤버 CRUD APIs", description = "멤버 조회, 생성, 수정, 삭제를 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtUserService jwtUserService;

    @Operation(summary = "로그인 API", description = "멤버 로그인 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "403", description = "로그인 실패"),
            @ApiResponse(responseCode = "404", description = "해당 멤버를 찾을 수 없음")
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResult<String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = jwtUserService.login(loginRequest);
        return ResponseEntity.ok()
                .header("Authorization", token)
                .body(ApiUtils.success("로그인 성공"));
    }

    @Operation(summary = "회원가입 API", description = "멤버 회원가입 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패"),
            @ApiResponse(responseCode = "409", description = "중복 회원 존재")
    })
    @PostMapping
    public ResponseEntity<ApiResult<MemberJoinSuccessResponse>> signUp(
            @RequestBody MemberJoinRequest request
    ) {
        MemberJoinSuccessResponse response = memberService.saveMember(request);
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }
}
