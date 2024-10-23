package develop.grassserver.member.profile.banner;

import develop.grassserver.BaseEntity;
import develop.grassserver.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Banner extends BaseEntity {

    @Column(length = 1024, nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;
}