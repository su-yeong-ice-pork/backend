package develop.grassserver.member.profile;

import develop.grassserver.BaseEntity;
import develop.grassserver.member.Freeze;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends BaseEntity {

    @Column(length = 1024, nullable = false)
    private String image;

    @Column(length = 100)
    private String message;

    @Column(length = 32, nullable = false)
    private String mainTitle;

    @Column(length = 1024, nullable = false)
    private String mainBanner;

    @Embedded
    private Freeze freeze;

    public void updateImage(String url) {
        this.image = url;
    }
}
