package develop.grassserver.member.profile.dto;

import develop.grassserver.member.profile.image.DefaultImage;
import java.util.List;

public record FindAllDefaultProfileImagesResponse(List<ImageUrl> profileImages) {

    public static FindAllDefaultProfileImagesResponse from(List<DefaultImage> images) {
        return new FindAllDefaultProfileImagesResponse(
                images.stream()
                        .map(image -> new ImageUrl(image.getUrl()))
                        .toList()
        );
    }

    public record ImageUrl(String url) {
    }
}
