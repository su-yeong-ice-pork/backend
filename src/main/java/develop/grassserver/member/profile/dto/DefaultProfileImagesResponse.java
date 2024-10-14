package develop.grassserver.member.profile.dto;

import develop.grassserver.member.profile.image.DefaultImage;
import java.util.List;

public record DefaultProfileImagesResponse(List<ImageUrl> profileImages) {

    public static DefaultProfileImagesResponse from(List<DefaultImage> images) {
        return new DefaultProfileImagesResponse(
                images.stream()
                        .map(image -> new ImageUrl(image.getUrl()))
                        .toList()
        );
    }

    public record ImageUrl(String url) {
    }
}
