package develop.grassserver.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyRecord {
    @Column(name = "total_study_time_seconds", nullable = false)
    @ColumnDefault("0")
    private Duration totalStudyTime = Duration.ZERO;

    private int topStreak;

    public void updateTotalStudyTime(Duration todayStudyTime) {
        this.totalStudyTime = this.totalStudyTime.plus(todayStudyTime);
    }
}
