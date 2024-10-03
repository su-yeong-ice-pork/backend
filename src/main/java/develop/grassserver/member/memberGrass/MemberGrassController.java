package develop.grassserver.member.memberGrass;

import develop.grassserver.member.memberGrass.dto.MemberStreakResponse;
import develop.grassserver.member.memberGrass.dto.YearlyTotalGrassResponse;
import develop.grassserver.utils.ApiUtils;
import develop.grassserver.utils.ApiUtils.ApiResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버 스트릭 관련 조회 APIs", description = "현재 스트릭, 최장 스트릭, 연간 잔디 등을 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/{id}")
public class MemberGrassController {
    private final MemberGrassService memberGrassService;

    @GetMapping("/record")
    public ResponseEntity<ApiResult<MemberStreakResponse>> getMemberStreak(@PathVariable("id") Long memberId) {
        return ResponseEntity.ok(ApiUtils.success(memberGrassService.getMemberStreak(memberId)));
    }

    @GetMapping("/grass/yearly")
    public ResponseEntity<ApiResult<YearlyTotalGrassResponse>> getYearlyTotalGrass(@PathVariable("id") Long memberId,
                                                                                   @RequestParam("year") int year) {
        return ResponseEntity.ok(ApiUtils.success(memberGrassService.getYearlyGrass(memberId, year)));
    }
}
