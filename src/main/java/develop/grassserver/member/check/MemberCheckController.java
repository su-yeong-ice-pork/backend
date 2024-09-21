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

    @GetMapping("/id")
    public ResponseEntity<ApiUtils.ApiResult<?>> checkId(@RequestParam String memberId) {
        memberCheckService.checkMemberId(memberId);
        return ResponseEntity.ok()
                .body(ApiUtils.success(null));
    }
}
