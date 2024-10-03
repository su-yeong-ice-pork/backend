package develop.grassserver.member.dto;

public record MemberStreakResponse(
        int currentStreak,
        int longestStreak,
        String totalStudyTime
) {
}
