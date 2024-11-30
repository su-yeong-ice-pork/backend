package develop.grassserver.randomStudy.domain.entity;

import develop.grassserver.common.BaseEntity;
import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE random_study_member SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class RandomStudyMember extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private RandomStudy randomStudy;
}
