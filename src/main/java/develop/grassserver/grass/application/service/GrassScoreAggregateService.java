package develop.grassserver.grass.application.service;

import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.grass.infrastructure.repositiory.GrassScoreAggregateQueryRepository;
import develop.grassserver.grass.infrastructure.repositiory.GrassScoreAggregateRepository;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.rank.application.dto.MajorRankingData;
import develop.grassserver.rank.application.dto.StudyRankingData;
import develop.grassserver.rank.application.service.GrassScoreRankingUpdateService;
import develop.grassserver.rank.presentation.dto.IndividualRankingResponse;
import develop.grassserver.rank.presentation.dto.MajorRankingResponse;
import develop.grassserver.rank.presentation.dto.StudyRankingResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GrassScoreAggregateService {

    private final GrassScoreRankingUpdateService grassScoreRankingUpdateService;

    private final GrassScoreAggregateRepository grassScoreAggregateRepository;
    private final GrassScoreAggregateQueryRepository aggregateQueryRepository;

    public void calculateGrassScoreRanking() {
        try {
            grassScoreRankingUpdateService.updateGrassAggregateScore();
            log.info("출석 점수 UPDATE 성공");
        } catch (Exception exception) {
            log.error("출석 점수 UPDATE 실패 = {}", exception.getMessage());
        }

        List<GrassScoreAggregate> grassScoreAggregates = aggregateQueryRepository.getTopMembers();

        try {
            grassScoreRankingUpdateService
                    .saveIndividualGrassScoreRanking(IndividualRankingResponse.from(grassScoreAggregates));
            log.info("랭킹 저장 성공");
        } catch (Exception exception) {
            log.error("랭킹 저장 실패 = {}", exception.getMessage());
        }
    }

    public void calculateStudyRanking() {
        List<StudyRankingData> studies = aggregateQueryRepository.getTopStudies();

        try {
            grassScoreRankingUpdateService.saveStudyRanking(StudyRankingResponse.from(studies));
            log.info("스터디 랭킹 저장 성공");
        } catch (Exception exception) {
            log.error("스터디 랭킹 저장 실패 = {}", exception.getMessage());
        }
    }

    public void calculateMajorRanking() {
        List<MajorRankingData> majors = aggregateQueryRepository.getTopMajors();

        try {
            grassScoreRankingUpdateService.saveMajorRanking(MajorRankingResponse.from(majors));
            log.info("학과 랭킹 저장 성공");
        } catch (Exception exception) {
            log.error("학과 랭킹 저장 실패 = {}", exception.getMessage());
        }
    }

    public void subtractGrassScore(int score, Member member) {
        GrassScoreAggregate grassScoreAggregate = grassScoreAggregateRepository.findByMember(member)
                .orElseThrow(EntityNotFoundException::new);
        grassScoreAggregate.subtractScore(score);
    }
}
