package develop.grassserver.profile.presentation.controller;

import develop.grassserver.common.annotation.LoginMember;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.profile.application.service.FreezeService;
import develop.grassserver.profile.presentation.dto.FreezeCountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/freeze")
public class FreezeController {

    private final FreezeService freezeService;

    @GetMapping
    public ResponseEntity<ApiResult<FreezeCountResponse>> getFreezeCount(@LoginMember Member member) {
        FreezeCountResponse response = freezeService.getFreezeCount(member);
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResult<String>> exchangeFreeze(@LoginMember Member member) {
        freezeService.exchangeFreeze(member);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }

    @PatchMapping
    public ResponseEntity<ApiResult<String>> useFreeze(@LoginMember Member member) {
        freezeService.useFreeze(member);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }
}
