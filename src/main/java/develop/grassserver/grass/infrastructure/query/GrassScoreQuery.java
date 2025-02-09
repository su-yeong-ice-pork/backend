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

    public static final String STUDIES_BY_STUDY_TIME_SELECT_QUERY =
            "SELECT new StudyRankingData(" +
                    "s.id, s.name, s.totalStudyTime, COUNT(sm) ) " +
                    "FROM Study s " +
                    "LEFT JOIN s.members sm WITH sm.status = true " +
                    "WHERE s.status = true " +
                    "GROUP BY s.id, s.name, s.totalStudyTime " +
                    "ORDER BY s.totalStudyTime DESC";

    public static final String MAJOR_RANKING_DATA_SELECT_QUERY =
            "SELECT new develop.grassserver.rank.application.dto.MajorRankingData("
                    + "m.major.college, "
                    + "COUNT(m), "
                    + "SUM(m.studyRecord.totalStudyTime), "
                    + "SUM(gsa.grassScore)"
                    + ") "
                    + "FROM Member m "
                    + "JOIN GrassScoreAggregate gsa ON gsa.member = m "
                    + "GROUP BY m.major.college "
                    + "ORDER BY SUM(gsa.grassScore) DESC";

    private GrassScoreQuery() {
    }
}
