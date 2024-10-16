package develop.grassserver.member.memberGrass.dto;

public record MemberTotalStreakResponse(
        String createdDate,
        long totalStreak,
        int totalStudyTime
) {
}
