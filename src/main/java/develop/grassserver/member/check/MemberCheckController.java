package develop.grassserver.member.check;

import develop.grassserver.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/check")
public class MemberCheckController {

    private final MemberCheckService memberCheckService;

    @GetMapping("/name")
    public ResponseEntity<ApiUtils.ApiResult<?>> checkName(@RequestParam String name) {
        memberCheckService.checkMemberName(name);
        return ResponseEntity.ok()
                .body(ApiUtils.success(null));
    }

    @GetMapping("/email")
    public ResponseEntity<ApiUtils.ApiResult<?>> checkEmail(@RequestParam String email) {
        memberCheckService.checkMemberEmail(email);
        return ResponseEntity.ok()
                .body(ApiUtils.success(null));
    }
}
