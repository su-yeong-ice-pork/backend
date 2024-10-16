package develop.grassserver.member.memberGrass.dto;

import java.time.LocalDate;

public record MemberTotalStreakResponse(
        LocalDate createdDate,
        long totalStreak,
        int totalStudyTime
) {
}
