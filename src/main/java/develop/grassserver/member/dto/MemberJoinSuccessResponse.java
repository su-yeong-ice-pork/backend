package develop.grassserver.member.dto;

import develop.grassserver.member.Member;

public record MemberJoinSuccessResponse(JoinMember member) {

    public static MemberJoinSuccessResponse from(Member member) {
        return new MemberJoinSuccessResponse(
                new JoinMember(
                        member.getId(),
                        member.getName(),
                        member.getEmail()
                )
        );
    }

    public record JoinMember(Long id, String name, String email) {
    }
}
