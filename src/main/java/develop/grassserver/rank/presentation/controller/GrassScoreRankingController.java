package develop.grassserver.rank.presentation.controller;

import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.rank.application.service.GrassScoreRankingService;
import develop.grassserver.rank.presentation.dto.GrassScoreIndividualRankingResponse;
import develop.grassserver.rank.presentation.dto.GrassScoreMajorRankingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ranks")
public class GrassScoreRankingController {

    private final GrassScoreRankingService rankingService;

    @GetMapping("/individual")
    public ResponseEntity<ApiResult<GrassScoreIndividualRankingResponse>> getIndividualRanking() {
        GrassScoreIndividualRankingResponse response = rankingService.getIndividualRanking();
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }

    @GetMapping("/major")
    public ResponseEntity<ApiResult<GrassScoreMajorRankingResponse>> getMajorRanking() {
        GrassScoreMajorRankingResponse response = rankingService.getMajorRanking();
        return ResponseEntity.ok()
                .body(ApiUtils.success(response));
    }
}
