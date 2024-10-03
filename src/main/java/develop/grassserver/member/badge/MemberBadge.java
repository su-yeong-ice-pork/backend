package develop.grassserver.member.badge;

import develop.grassserver.BaseEntity;
import develop.grassserver.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
public class MemberBadge extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Badge badge;

    @ColumnDefault("true")
    @Column(nullable = false)
    private boolean isChecked = true;
}
