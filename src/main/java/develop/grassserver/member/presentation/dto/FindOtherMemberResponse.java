package develop.grassserver.member.presentation.dto;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.profile.domain.entity.Profile;

public record FindOtherMemberResponse(boolean isMyFriend, MemberDTO member) {

    public static FindOtherMemberResponse from(boolean isMyFriend, Member member) {
        Profile profile = member.getProfile();
        return new FindOtherMemberResponse(
                isMyFriend,
                new MemberDTO(
                        member.getId(),
                        member.getName(),
                        profile.getMessage(),
                        profile.getImage(),
                        profile.getMainTitle(),
                        profile.getMainBanner()
                )
        );
    }

    public record MemberDTO(
            Long id,
            String name,
            String message,
            String profileImage,
            String mainTitle,
            String mainBanner
    ) {
    }
}
