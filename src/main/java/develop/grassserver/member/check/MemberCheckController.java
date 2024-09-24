package develop.grassserver.member.check;

import develop.grassserver.mail.MailService;
import develop.grassserver.member.auth.RedisService;
import develop.grassserver.member.check.dto.CheckAuthCodeRequest;
import develop.grassserver.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/check")
public class MemberCheckController {

    private final MailService mailService;
    private final RedisService redisService;
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
        mailService.sendMail(email);
        return ResponseEntity.ok()
                .body(ApiUtils.success(null));
    }

    @PostMapping("/code")
    public ResponseEntity<ApiUtils.ApiResult<?>> checkCode(@RequestBody CheckAuthCodeRequest request) {
        redisService.checkAuthCode(request);
        return ResponseEntity.ok()
                .body(ApiUtils.success(null));
    }
}
