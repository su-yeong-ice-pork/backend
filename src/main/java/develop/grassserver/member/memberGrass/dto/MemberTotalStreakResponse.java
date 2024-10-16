package develop.grassserver.member.memberGrass.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record MemberTotalStreakResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일")
        LocalDate createdDate,
        long totalStreak,
        int totalStudyTime
) {
}
