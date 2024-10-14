package develop.grassserver.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyRecord {
    @Column(name = "total_study_time_seconds", nullable = false)
    @ColumnDefault("0")
    @Builder.Default
    private Duration totalStudyTime = Duration.ZERO;

    @ColumnDefault("0")
    @Builder.Default
    private int topStreak = 0;

    public void updateTotalStudyTime(Duration todayStudyTime) {
        this.totalStudyTime = this.totalStudyTime.plus(todayStudyTime);
    }

    public void updateTopStreak(int currentStreak) {
        this.topStreak = currentStreak;
    }
}
