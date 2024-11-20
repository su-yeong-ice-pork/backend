package develop.grassserver.requestNotification.domain.entity;

import develop.grassserver.common.BaseEntity;
import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public abstract class RequestNotification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member receiver;
}
