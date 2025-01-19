package develop.grassserver.rank.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.rank.presentation.dto.IndividualRankingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GrassScoreRankingService {

    private final RedisService redisService;

    public IndividualRankingResponse getIndividualRanking() {
        try {
            return redisService.getIndividualGrassScoreRanking();
        } catch (JsonProcessingException exception) {
            log.error("개인 랭킹 조회 중 예외 발생 = {}", exception.getMessage());
            throw new IllegalStateException("서버 오류 발생");
        }
    }
}
