package develop.grassserver.grass.presentation.controller;

import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.grass.application.service.MemberGrassService;
import develop.grassserver.grass.presentation.dto.MemberStreakResponse;
import develop.grassserver.grass.presentation.dto.MonthlyTotalGrassResponse;
import develop.grassserver.grass.presentation.dto.YearlyTotalGrassResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버 스트릭 관련 조회 APIs", description = "스트릭, 연간 잔디 등을 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/{id}")
public class MemberGrassController {

    private final MemberGrassService memberGrassService;

    @Operation(summary = "사용자 스트릭 조회 API", description = "사용자 최장 스트릭, 현재 스트릭, 총 공부시간 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패, 재로그인 필요"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    @GetMapping("/record")
    public ResponseEntity<ApiResult<MemberStreakResponse>> getMemberStreak(@PathVariable("id") Long memberId) {
        return ResponseEntity.ok(ApiUtils.success(memberGrassService.getMemberStreak(memberId)));
    }

    @Operation(summary = "연간 잔디 조회 API", description = "연간 잔데 전체 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패, 재로그인 필요"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    @GetMapping("/grass/yearly")
    public ResponseEntity<ApiResult<YearlyTotalGrassResponse>> getYearlyTotalGrass(@PathVariable("id") Long memberId,
                                                                                   @RequestParam("year") int year) {
        return ResponseEntity.ok(ApiUtils.success(memberGrassService.getYearlyGrass(memberId, year)));
    }

    @Operation(summary = "월간 잔디 조회 API", description = "월간 전체 잔디 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패, 재로그인 필요"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자")
    })
    @GetMapping("/grass/monthly")
    public ResponseEntity<ApiResult<MonthlyTotalGrassResponse>> getMonthlyTotalGrass(@PathVariable("id") Long memberId,
                                                                                     @RequestParam("year") int year,
                                                                                     @RequestParam("month") int month) {
        return ResponseEntity.ok(ApiUtils.success(memberGrassService.getMonthlyGrass(memberId, year, month)));
    }
}
