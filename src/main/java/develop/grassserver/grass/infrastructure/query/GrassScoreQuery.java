package develop.grassserver.grass.infrastructure.query;

public final class GrassScoreQuery {

    public static final String MEMBER_TOTAL_GRASS_SCORE_UPDATE_QUERY =
            "UPDATE grass_score_aggregate gsa " +
                    "JOIN grass g ON gsa.member_id = g.member_id " +
                    "SET gsa.grass_score = gsa.grass_score + g.grass_score, " +
                    "gsa.updated_at = NOW() " +
                    "WHERE g.attendance_date = :yesterday " +
                    "AND g.status = true";

    public static final String MEMBER_TOTAL_GRASS_SCORE_SELECT_QUERY =
            "SELECT gsa FROM GrassScoreAggregate gsa " +
                    "WHERE gsa.member.status = TRUE " +
                    "ORDER BY gsa.grassScore DESC";

    private GrassScoreQuery() {
    }
}

