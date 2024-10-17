package develop.grassserver.grass.presentation.dto;

public record MemberStreakResponse(
        int currentStreak,
        int maxStreak,
        int totalStudyTime
) {
}
