package develop.grassserver.randomStudy.domain.entity;

import develop.grassserver.common.BaseEntity;
import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE random_study_application SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class RandomStudyApplication extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @Column(nullable = false)
    private LocalDate attendanceDate;

    @Column(nullable = false)
    private LocalTime attendanceTime;
}
