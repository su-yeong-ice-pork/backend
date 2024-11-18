package develop.grassserver.friend.presentation.controller;

import develop.grassserver.common.annotation.LoginMember;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.friend.application.service.FriendService;
import develop.grassserver.friend.presentation.dto.RequestFriendRequest;
import develop.grassserver.member.domain.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendController {

    private final FriendService friendService;

    @Operation(summary = "친구 추가 API", description = "친구 추가 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "친구 추가 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "400", description = "친구 추가 실패"),
            @ApiResponse(responseCode = "404", description = "상대 멤버 정보를 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "이미 등록된 친구")
    })
    @PostMapping
    public ResponseEntity<ApiResult<String>> requestFriend(
            @LoginMember Member member,
            @RequestBody RequestFriendRequest request
    ) {
        friendService.requestFriend(member, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }
}
