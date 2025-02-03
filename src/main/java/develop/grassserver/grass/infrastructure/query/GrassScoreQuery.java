package develop.grassserver.grass.infrastructure.query;

import lombok.Getter;

@Getter
public enum GrassScoreQuery {
    MEMBER_TOTAL_GRASS_SCORE_UPDATE(
            "UPDATE grass_score_aggregate gsa " +
                    "JOIN grass g ON gsa.member_id = g.member_id " +
                    "SET gsa.grass_score = gsa.grass_score + g.grass_score, " +
                    "    gsa.updated_at = NOW() " +
                    "WHERE g.attendance_date = :yesterday " +
                    "  AND g.status = true"
    ),
    MEMBER_TOTAL_GRASS_SCORE_SELECT(
            "SELECT gsa FROM GrassScoreAggregate gsa WHERE gsa.member.status = TRUE ORDER BY gsa.grassScore DESC"
    );

    private final String query;

    GrassScoreQuery(String query) {
        this.query = query;
    }
}
