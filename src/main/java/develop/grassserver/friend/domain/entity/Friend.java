package develop.grassserver.friend.domain.entity;

import develop.grassserver.common.BaseEntity;
import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member1_id", "member2_id"})
})
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member2;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FriendRequestStatus requestStatus;

    public void disconnect() {
        this.requestStatus = FriendRequestStatus.DELETED;
    }
}
