package develop.grassserver.friend.presentation.dto;

import develop.grassserver.common.utils.duration.DurationUtils;
import develop.grassserver.member.domain.entity.Member;
import java.util.List;
import java.util.Map;

public record FindAllFriendsResponse(List<FriendDTO> friends) {

    public static FindAllFriendsResponse from(List<Member> members, Map<Long, Boolean> friendStudyStatus) {
        return new FindAllFriendsResponse(
                members.stream()
                        .map(member ->
                                new FriendDTO(
                                        member.getId(),
                                        member.getName(),
                                        member.getProfile().getMessage(),
                                        member.getProfile().getImage(),
                                        DurationUtils.formatHourAndMinute(member.getStudyRecord().getTotalStudyTime()),
                                        friendStudyStatus.get(member.getId())
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
