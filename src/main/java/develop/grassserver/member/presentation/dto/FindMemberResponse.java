package develop.grassserver.member.presentation.dto;

import develop.grassserver.member.domain.entity.Member;

public record FindMemberResponse(MemberDTO member) {

    public static FindMemberResponse from(Member member) {
        return new FindMemberResponse(
                new MemberDTO(
                        member.getId(),
                        member.getName(),
                        member.getProfile().getImage()
                )
        );
    }

    public record MemberDTO(Long id, String name, String profileImage) {
    }
}
