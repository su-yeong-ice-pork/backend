package develop.grassserver.randomStudy.presentation.controller;

import develop.grassserver.common.annotation.LoginMember;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.randomStudy.application.service.RandomStudyApplicationService;
import develop.grassserver.randomStudy.application.service.RandomStudyService;
import develop.grassserver.randomStudy.presentation.dto.ApplyRandomStudyRequest;
import develop.grassserver.randomStudy.presentation.dto.FindAllRandomStudyMembersResponse;
import develop.grassserver.randomStudy.presentation.dto.RandomStudyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/random-studies")
public class RandomStudyController {

    private final RandomStudyService randomStudyService;
    private final RandomStudyApplicationService applicationService;

    @Operation(summary = "랜덤 스터디 상세 조회 API", description = "랜덤 스터디 상세 조회 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 조회 성공"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패"),
            @ApiResponse(responseCode = "404", description = "해당하는 스터디가 없음")
    })
    @GetMapping
    public ResponseEntity<ApiResult<RandomStudyResponse>> getRandomStudyDetail(
            @LoginMember Member member) {
        RandomStudyResponse response = randomStudyService.getRandomStudyDetail(member);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @Operation(summary = "랜덤 스터디 구성원 전체 조회 API", description = "랜덤 스터디 구성원 전체 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 구성원 조회 성공"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패")
    })
    @GetMapping("/{studyId}/participants")
    public ResponseEntity<ApiResult<FindAllRandomStudyMembersResponse>> getAllRandomStudyMembers(
            @PathVariable Long studyId, @LoginMember Member member) {
        FindAllRandomStudyMembersResponse response = randomStudyService.getAllRandomStudyMembers(member, studyId);
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }

    @PostMapping("/application")
    public ResponseEntity<ApiResult<String>> applyRandomStudy(@RequestBody ApplyRandomStudyRequest request,
                                                              @LoginMember Member member) {

        applicationService.applyRandomStudy(member, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiUtils.success());
    }
}
