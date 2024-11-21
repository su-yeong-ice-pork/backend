package develop.grassserver.study.presentation.controller;

import develop.grassserver.common.annotation.LoginMember;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.study.application.service.StudyService;
import develop.grassserver.study.presentation.dto.CreateStudyRequest;
import develop.grassserver.study.presentation.dto.CreateStudyResponse;
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
@RequestMapping("/api/v1/regular-studies")
public class StudyController {
    private final StudyService studyService;

    @Operation(summary = "고정 스터디 생성 API", description = "고정 스터디 생성 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "스터디 생성 성공"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패")
    })
    @PostMapping
    public ResponseEntity<ApiResult<CreateStudyResponse>> createStudy(@RequestBody CreateStudyRequest request,
                                                                      @LoginMember Member member) {
        CreateStudyResponse response = studyService.createStudy(member, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success(response));
    }

}
