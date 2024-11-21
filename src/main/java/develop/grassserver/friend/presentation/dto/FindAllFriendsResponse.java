package develop.grassserver.friend.presentation.dto;

import develop.grassserver.grass.application.dto.MemberStudyInfoDTO;
import java.util.List;

public record FindAllFriendsResponse(List<FriendDTO> friends) {

    public static FindAllFriendsResponse from(MemberStudyInfoDTO studyInfo) {
        return new FindAllFriendsResponse(
                studyInfo.members().stream()
                        .map(member ->
                                new FriendDTO(
                                        member.getId(),
                                        member.getName(),
                                        member.getProfile().getMessage(),
                                        member.getProfile().getImage(),
                                        studyInfo.studyTimes().get(member.getId()),
                                        studyInfo.studyStatuses().get(member.getId())
                                )
                        )
                        .toList()
        );
    }


    public record FriendDTO(
            Long id,
            String name,
            String message,
            String profileImage,
            String todayStudyTime,
            boolean studyStatus
    ) {
    }
}
