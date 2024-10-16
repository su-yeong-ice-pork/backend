package develop.grassserver.profile.domain.entity.image;

import java.util.List;
import lombok.Getter;

@Getter
public enum DefaultImage {

    PROFILE_IMAGE1("https://grass-bucket.s3.us-east-2.amazonaws.com/profileImage1.png"),
    PROFILE_IMAGE2("https://grass-bucket.s3.us-east-2.amazonaws.com/profileImage2.png"),
    PROFILE_IMAGE3("https://grass-bucket.s3.us-east-2.amazonaws.com/profileImage3.png"),
    PROFILE_IMAGE4("https://grass-bucket.s3.us-east-2.amazonaws.com/profileImage4.png");

    private final String url;

    DefaultImage(String url) {
        this.url = url;
    }

    public static List<DefaultImage> getDefaultImages() {
        return List.of(PROFILE_IMAGE1, PROFILE_IMAGE2, PROFILE_IMAGE3, PROFILE_IMAGE4);
    }
}
