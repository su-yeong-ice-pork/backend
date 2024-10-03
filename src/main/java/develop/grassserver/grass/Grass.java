package develop.grassserver.grass;

import develop.grassserver.BaseEntity;
import develop.grassserver.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalTime;
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
public class Grass extends BaseEntity {
    @ColumnDefault("'00:00:00'")
    private LocalTime studyTime = LocalTime.of(0, 0);

    @ColumnDefault("1")
    private int currentStreak = 1;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
