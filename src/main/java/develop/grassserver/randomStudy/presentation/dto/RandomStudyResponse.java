package develop.grassserver.randomStudy.presentation.dto;

public record RandomStudyResponse(RandomStudyDetailResponse randomStudy) {

    public static RandomStudyResponse empty() {
        return new RandomStudyResponse(null);
    }

    public static RandomStudyResponse of(RandomStudyDetailResponse detailResponse) {
        return new RandomStudyResponse(detailResponse);
    }
}
