package develop.grassserver.randomStudy.domain.entity;

import develop.grassserver.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE randomstudy SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class RandomStudy extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime attendanceTime;

    @ColumnDefault("0")
    @Builder.Default
    @Column(nullable = false)
    private Duration totalStudyTime = Duration.ZERO;
}
