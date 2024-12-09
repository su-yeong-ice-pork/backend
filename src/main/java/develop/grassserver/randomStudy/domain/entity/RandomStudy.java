package develop.grassserver.randomStudy.domain.entity;

import develop.grassserver.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
@SQLDelete(sql = "UPDATE random_study SET status = false WHERE id = ?")
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
    
    @OneToMany(mappedBy = "randomStudy", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<RandomStudyMember> members = new ArrayList<>();
}
