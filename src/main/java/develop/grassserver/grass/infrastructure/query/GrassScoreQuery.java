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
                    "JOIN FETCH gsa.member m " +
                    "JOIN FETCH m.profile " +
                    "WHERE m.status = TRUE " +
                    "ORDER BY gsa.grassScore DESC";

    public static final String STUDIES_BY_STUDY_TIME_SELECT_QUERY =
            "SELECT new StudyRankingData(" +
                    "s.id, s.name, s.totalStudyTime, COUNT(sm) ) " +
                    "FROM Study s " +
                    "JOIN s.members sm " +
                    "WHERE s.status = true AND sm.status = true " +
                    "GROUP BY s.id, s.name, s.totalStudyTime " +
                    "ORDER BY s.totalStudyTime DESC";

    public static final String MAJOR_RANKING_DATA_SELECT_QUERY =
            "SELECT new MajorRankingData(" +
                    "m.major.department, " +
                    "COUNT(m), " +
                    "SUM(m.studyRecord.totalStudyTime), " +
                    "COALESCE(SUM(gsa.grassScore), 0)" +
                    ") " +
                    "FROM Member m " +
                    "LEFT JOIN GrassScoreAggregate gsa ON gsa.member = m " +
                    "WHERE m.status = true " +
                    "GROUP BY m.major.department " +
                    "ORDER BY SUM(gsa.grassScore) DESC";

    private GrassScoreQuery() {
    }
}
