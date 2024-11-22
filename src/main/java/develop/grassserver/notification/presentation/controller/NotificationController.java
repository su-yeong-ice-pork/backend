package develop.grassserver.notification.presentation.controller;

import develop.grassserver.common.annotation.LoginMember;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.notification.application.service.NotificationService;
import develop.grassserver.notification.presentation.dto.FindAllEmojiAndMessageNotificationsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "이모지, 메시지 알림 조회 API", description = "이모지, 메시지 알림 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "이모지, 메시지 알림 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @GetMapping
    public ResponseEntity<ApiResult<FindAllEmojiAndMessageNotificationsResponse>> findAllEmojiAndMessageNotifications(
            @LoginMember Member member
    ) {
        FindAllEmojiAndMessageNotificationsResponse response =
                notificationService.findAllEmojiAndMessageNotifications(member);
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }
}
