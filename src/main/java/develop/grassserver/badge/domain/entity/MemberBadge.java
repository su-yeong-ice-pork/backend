package develop.grassserver.badge.domain.entity;

import develop.grassserver.common.BaseEntity;
import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberBadge extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Badge badge;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean isChecked = false;
}
