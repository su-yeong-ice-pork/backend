package develop.grassserver.grass.domain.entity;

import develop.grassserver.common.BaseEntity;
import develop.grassserver.grass.presentation.exception.NotEnoughGrassScoreException;
import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GrassScoreAggregate extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @Column(nullable = false)
    @ColumnDefault("0")
    @Builder.Default
    private int grassScore = 0;

    @Version
    private int version;

    public void subtractScore(int subtractScore) {
        if (grassScore < subtractScore) {
            throw new NotEnoughGrassScoreException();
        }
        grassScore -= subtractScore;
    }
}
