package develop.grassserver.rank.presentation.controller;

import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.rank.application.service.GrassScoreRankingService;
import develop.grassserver.rank.presentation.dto.GrassScoreIndividualRankingResponse;
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
        rankingService.getIndividualRanking();
        return null;
    }
}
