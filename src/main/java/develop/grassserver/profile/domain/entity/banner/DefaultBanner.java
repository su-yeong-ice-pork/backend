package develop.grassserver.profile.domain.entity.banner;

import java.util.List;
import lombok.Getter;

@Getter
public enum DefaultBanner {

    BANNER_IMAGE1("https://grass-bucket.s3.us-east-2.amazonaws.com/bannerImage1.png"),
    BANNER_IMAGE2("https://grass-bucket.s3.us-east-2.amazonaws.com/bannerImage2.png"),
    BANNER_IMAGE3("https://grass-bucket.s3.us-east-2.amazonaws.com/bannerImage3.png"),
    BANNER_IMAGE4("https://grass-bucket.s3.us-east-2.amazonaws.com/bannerImage4.png");

    private final String url;

    DefaultBanner(String url) {
        this.url = url;
    }

    public static List<DefaultBanner> getDefaultImages() {
        return List.of(BANNER_IMAGE1, BANNER_IMAGE2, BANNER_IMAGE3, BANNER_IMAGE4);
    }
}

