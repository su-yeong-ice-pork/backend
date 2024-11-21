package develop.grassserver.study.domain.entity;

import develop.grassserver.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.time.Duration;
import java.util.List;
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
@SQLDelete(sql = "UPDATE study SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Study extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ColumnDefault("0")
    @Builder.Default
    @Column(nullable = false)
    private Duration totalStudyTime = Duration.ZERO;

    @OneToMany(mappedBy = "study")
    private List<StudyMember> members;
}
