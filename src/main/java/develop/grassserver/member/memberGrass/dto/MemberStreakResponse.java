package develop.grassserver.member.memberGrass.dto;

public record MemberStreakResponse(
        int currentStreak,
        int maxStreak,
        int totalStudyTime
) {
}
