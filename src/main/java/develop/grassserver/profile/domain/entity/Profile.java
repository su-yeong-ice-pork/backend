package develop.grassserver.profile.domain.entity;

import develop.grassserver.common.BaseEntity;
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

    public static final String DEFAULT_PROFILE_IMAGE =
            "https://grass-bucket.s3.us-east-2.amazonaws.com/%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%91%E1%85%B5%E1%86%AF%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B5.png";
    public static final String DEFAULT_BANNER_IMAGE = "https://grass-bucket.s3.us-east-2.amazonaws.com/bannerImage1.png";
    public static final String DEFAULT_PROFILE_MESSAGE = "오늘 하루도 화이팅!";
    public static final String DEFAULT_TITLE_NAME = "초심자";

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

    public void updateMainBanner(String url) {
        this.mainBanner = url;
    }

    public void updateMessage(String message) {
        this.message = message;
    }
}
