package develop.grassserver.requestNotification.presentation.controller;

import develop.grassserver.common.annotation.LoginMember;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.requestNotification.application.service.FriendRequestNotificationService;
import develop.grassserver.requestNotification.presentation.dto.FindAllFriendRequestsResponse;
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
@RequestMapping("/api/v1/request-notifications/friends")
public class FriendRequestNotificationController {

    private final FriendRequestNotificationService friendRequestNotificationService;

    @Operation(summary = "친구 요청 알림 조회 API", description = "친구 요청 알림 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "친구 요청 알림 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @GetMapping
    public ResponseEntity<ApiResult<FindAllFriendRequestsResponse>> findAllRequests(
            @LoginMember Member member
    ) {
        FindAllFriendRequestsResponse response = friendRequestNotificationService.findAllFriendRequests(member);
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }
}
