package develop.grassserver.study.presentation.controller;

import develop.grassserver.common.annotation.LoginMember;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.study.application.service.StudyCommandService;
import develop.grassserver.study.application.service.StudyQueryService;
import develop.grassserver.study.presentation.dto.CreateStudyRequest;
import develop.grassserver.study.presentation.dto.CreateStudyResponse;
import develop.grassserver.study.presentation.dto.EnterStudyRequest;
import develop.grassserver.study.presentation.dto.FindAllStudyMembersResponse;
import develop.grassserver.study.presentation.dto.FindAllStudyResponse;
import develop.grassserver.study.presentation.dto.StudyDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/regular-studies")
public class StudyController {

    private final StudyCommandService studyCommandService;
    private final StudyQueryService studyQueryService;

    @Operation(summary = "고정 스터디 세부 조회 API", description = "고정 스터디 세부 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 세부 조회 성공"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패"),
            @ApiResponse(responseCode = "404", description = "해당하는 스터디가 없음")
    })
    @GetMapping("/{studyId}")
    public ResponseEntity<ApiResult<StudyDetailResponse>> getStudyDetail(@PathVariable Long studyId,
                                                                         @LoginMember Member member) {
        StudyDetailResponse response = studyQueryService.getStudyDetail(member, studyId);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @Operation(summary = "고정 스터디 목록 조회 API", description = "고정 스터디 목록 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 목록 조회 성공"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패")
    })
    @GetMapping
    public ResponseEntity<ApiResult<FindAllStudyResponse>> getAllStudies(@LoginMember Member member) {
        FindAllStudyResponse response = studyQueryService.getAllStudies(member);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @Operation(summary = "고정 스터디 구성원 전체 조회 API", description = "고정 스터디 구성원 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 구성원 조회 성공"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패"),
            @ApiResponse(responseCode = "403", description = "스터디 구성원이 아님")
    })
    @GetMapping("{studyId}/participants")
    public ResponseEntity<ApiResult<FindAllStudyMembersResponse>> getAllStudyMembers(@PathVariable Long studyId,
                                                                                     @LoginMember Member member) {
        FindAllStudyMembersResponse response = studyQueryService.getAllStudyMembers(member, studyId);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @Operation(summary = "초대코드로 고정 스터디 입장 API", description = "초대코드로 고정 스터디 입장 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "스터디 입장 성공"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패"),
            @ApiResponse(responseCode = "400", description = "잘못된 초대코드")
    })
    @PostMapping("/entries")
    public ResponseEntity<ApiResult<String>> enterStudyWithInviteCode(
            @RequestBody EnterStudyRequest request, @LoginMember Member member) {
        studyCommandService.enterStudyWithInviteCode(member, request.inviteCode());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }

    @Operation(summary = "고정 스터디 생성 API", description = "고정 스터디 생성 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "스터디 생성 성공"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패")
    })
    @PostMapping
    public ResponseEntity<ApiResult<CreateStudyResponse>> createStudy(@RequestBody CreateStudyRequest request,
                                                                      @LoginMember Member member) {
        CreateStudyResponse response = studyCommandService.createStudy(member, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success(response));
    }

    @Operation(summary = "고정 스터디 나가기(구성원) API", description = "스터디 구성원이 고정 스터디를 나갈 때 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 세부 조회 성공"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패"),
            @ApiResponse(responseCode = "404", description = "해당하는 스터디가 없음"),
            @ApiResponse(responseCode = "403", description = "스터디 구성원이 아님"),
            @ApiResponse(responseCode = "400", description = "리더는 방을 나갈 수 없음")
    })
    @DeleteMapping("/{studyId}/participants")
    public ResponseEntity<ApiResult<String>> goOutStudy(@PathVariable Long studyId,
                                                        @LoginMember Member member) {
        studyCommandService.goOutStudy(member, studyId);
        return ResponseEntity.ok(ApiUtils.success());
    }
}
