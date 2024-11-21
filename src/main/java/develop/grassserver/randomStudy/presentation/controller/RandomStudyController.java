package develop.grassserver.randomStudy.presentation.controller;

import develop.grassserver.common.annotation.LoginMember;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.randomStudy.application.service.RandomStudyService;
import develop.grassserver.randomStudy.presentation.dto.RandomStudyDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/random-studies")
public class RandomStudyController {

    private RandomStudyService randomStudyService;

    @Operation(summary = "랜덤 스터디 상세 조회 API", description = "랜덤 스터디 상세 조회 조회 시 사용되는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스터디 조회 성공"),
            @ApiResponse(responseCode = "401", description = "멤버 인증 실패"),
            @ApiResponse(responseCode = "404", description = "해당하는 스터디가 없음")
    })
    @GetMapping("/{studyId}")
    public ResponseEntity<ApiResult<RandomStudyDetailResponse>> getRandomStudyDetail(@PathVariable Long studyId,
                                                                                     @LoginMember Member member) {
        RandomStudyDetailResponse response = randomStudyService.getRandomStudyDetail(member, studyId);
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
