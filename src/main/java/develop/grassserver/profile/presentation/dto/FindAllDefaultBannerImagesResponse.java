package develop.grassserver.member.profile.dto;

import develop.grassserver.profile.domain.entity.banner.DefaultBanner;
import java.util.List;

public record FindAllDefaultBannerImagesResponse(List<BannerUrl> bannerImages) {

    public static FindAllDefaultBannerImagesResponse from(List<DefaultBanner> banners) {
        return new FindAllDefaultBannerImagesResponse(
                banners.stream()
                        .map(banner -> new BannerUrl(banner.getUrl()))
                        .toList()
        );
    }

    public record BannerUrl(String url) {
    }
}
