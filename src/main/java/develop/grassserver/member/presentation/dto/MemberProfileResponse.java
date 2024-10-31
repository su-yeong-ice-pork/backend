package develop.grassserver.member.presentation.dto;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.profile.domain.entity.Profile;

public record MemberProfileResponse(MemberProfile member) {

    public static MemberProfileResponse from(Member member) {
        Profile profile = member.getProfile();
        return new MemberProfileResponse(
                new MemberProfile(
                        member.getId(),
                        member.getName(),
                        profile.getMessage(),
                        profile.getImage(),
                        profile.getMainTitle(),
                        profile.getMainBanner(),
                        profile.getFreeze().getFreezeCount(),
                        0, 0)
        );
    }

    public record MemberProfile(
            Long id,
            String name,
            String message,
            String profileImage,
            String mainTitle,
            String mainBanner,
            int freezeCount,
            int friendCount,
            int studyCount
    ) {
    }
}
