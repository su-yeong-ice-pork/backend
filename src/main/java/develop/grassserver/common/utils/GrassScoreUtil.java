package develop.grassserver.common.utils;

import java.time.Duration;

public class GrassScoreUtil {
    private static final int MAX_SCORE = 100;
    private static final int BASE_SCORE = 10;
    private static final double SCORE_PER_MINUTE = (90.0 / 8) / 60;

    public static int calculateStudyScore(Duration studyTime) {
        int calculatedScore = BASE_SCORE + (int) (SCORE_PER_MINUTE * studyTime.toMinutes());
        return Math.min(calculatedScore, MAX_SCORE);
    }
}
