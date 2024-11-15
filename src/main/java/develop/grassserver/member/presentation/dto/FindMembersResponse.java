package develop.grassserver.member.presentation.dto;

import develop.grassserver.member.domain.entity.Member;
import java.util.List;

public record FindMembersResponse(List<MemberDTO> members) {

    public static FindMembersResponse from(List<Member> members) {
        return new FindMembersResponse(
                members.stream()
                        .map(member ->
                                new MemberDTO(
                                        member.getId(),
                                        member.getName(),
                                        member.getProfile().getImage()
                                )
                        )
                        .toList()
        );
    }

    public record MemberDTO(Long id, String name, String profileImage) {
    }
}
