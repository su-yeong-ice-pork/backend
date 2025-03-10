package develop.grassserver.member.presentation.controller;

import develop.grassserver.common.annotation.LoginMember;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.member.application.service.MemberService;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.member.presentation.dto.ChangePasswordRequest;
import develop.grassserver.member.presentation.dto.DeleteMemberRequest;
import develop.grassserver.member.presentation.dto.FindMemberResponse;
import develop.grassserver.member.presentation.dto.FindOtherMemberResponse;
import develop.grassserver.member.presentation.dto.MemberJoinRequest;
import develop.grassserver.member.presentation.dto.MemberProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버 CRUD APIs", description = "멤버 조회, 생성, 수정, 삭제를 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "내정보 조회 API", description = "내정보 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내정보 조회 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패"),
    })
    @GetMapping
    public ResponseEntity<ApiResult<MemberProfileResponse>> findMember(
            @LoginMember Member member
    ) {
        MemberProfileResponse response = memberService.findMemberProfile(member);
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }


    @Operation(summary = "회원가입 API", description = "멤버 회원가입 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "400", description = "회원가입 실패"),
            @ApiResponse(responseCode = "409", description = "중복 회원 존재")
    })
    @PostMapping
    public ResponseEntity<ApiResult<String>> signUp(@Valid @RequestBody MemberJoinRequest request) {
        memberService.saveMember(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }

    @Operation(summary = "비밀번호 재설정 API", description = "비밀번호 재설정 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패. 이름과 이메일이 불일치"),
            @ApiResponse(responseCode = "404", description = "정보를 찾을 수 없음"),
    })
    @PatchMapping
    public ResponseEntity<ApiResult<String>> updatePassword(@Valid @RequestBody ChangePasswordRequest request) {
        memberService.changeMemberPassword(request);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }

    @Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "정보를 찾을 수 없음"),
    })
    @DeleteMapping
    public ResponseEntity<ApiResult<String>> deleteMember(
            @LoginMember Member member,
            @Valid @RequestBody DeleteMemberRequest request
    ) {
        memberService.deleteMember(request, member);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }

    @Operation(summary = "멤버 검색 API", description = "멤버 검색 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "멤버 검색 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없음.")
    })
    @GetMapping("/search")
    public ResponseEntity<ApiResult<FindMemberResponse>> findMemberByNameOrEmail(@RequestParam String keyword) {
        FindMemberResponse response = memberService.findMemberByNameOrEmail(keyword);
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }

    @Operation(summary = "상대 멤버 조회 API", description = "상대 멤버 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상대 멤버 조회 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "404", description = "해당 멤버를 찾을 수 없음.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<FindOtherMemberResponse>> findOtherMember(
            @PathVariable Long id,
            @LoginMember Member member
    ) {
        FindOtherMemberResponse response = memberService.findOtherMember(id, member);
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }
}
