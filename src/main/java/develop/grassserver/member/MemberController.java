package develop.grassserver.member;

import develop.grassserver.member.login.JwtUserService;
import develop.grassserver.member.login.LoginRequest;
import develop.grassserver.utils.ApiUtils;
import develop.grassserver.utils.ApiUtils.ApiResult;
import jakarta.validation.Valid;
import develop.grassserver.member.dto.MemberJoinRequest;
import develop.grassserver.member.dto.MemberJoinSuccessResponse;
import develop.grassserver.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final JwtUserService jwtUserService;

    @PostMapping("/login")
    public ResponseEntity<ApiResult<String>> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = jwtUserService.login(loginRequest);
        return ResponseEntity.ok()
                .header("Authorization", token)
                .body(ApiUtils.success("로그인 성공"));

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiUtils.ApiResult<MemberJoinSuccessResponse>> signUp(
            @RequestBody MemberJoinRequest request
    ) {
        MemberJoinSuccessResponse response = memberService.saveMember(request);
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }
}
