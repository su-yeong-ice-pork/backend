package develop.grassserver.grass.domain.entity;

import develop.grassserver.common.BaseEntity;
import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import java.time.Duration;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE grass SET status = false WHERE id = ?")
@SQLRestriction("status = true")
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "attendance_date"})
)
public class Grass extends BaseEntity {

    @Column(name = "study_time_seconds", nullable = false)
    @ColumnDefault("0")
    @Builder.Default
    private Duration studyTime = Duration.ZERO;

    @ColumnDefault("1")
    @Builder.Default
    private int currentStreak = 1;

    @ColumnDefault("10")
    @Builder.Default
    private int grassScore = 10;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, updatable = false)
    private LocalDate attendanceDate;

    @Version
    private Long version;

    @PrePersist
    private void prePersist() {
        this.attendanceDate = this.getCreatedAt().toLocalDate();
    }

    public void updateStudyTime(Duration todayStudyTime) {
        member.getStudyRecord().updateTotalStudyTime(todayStudyTime);
        this.studyTime = todayStudyTime.plus(studyTime);
    }

    public void updateGrassScore(int grassScore) {
        this.grassScore = grassScore;
    }
}
