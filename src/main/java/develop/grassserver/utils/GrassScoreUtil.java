package develop.grassserver.utils;

public class GrassScoreUtil {
    private static final int MAX_SCORE = 100;
    private static final int BASE_SCORE = 10;
    private static final int SCORE_PER_HOUR = 90 / 8;

    public static int calculateStudyScore(double duration) {
        int calculatedScore = (int) (BASE_SCORE + (SCORE_PER_HOUR * duration));
        return Math.min(calculatedScore, MAX_SCORE);
    }
}
