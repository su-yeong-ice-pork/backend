package develop.grassserver.member.badge.dto;

import develop.grassserver.member.badge.MemberBadge;
import java.util.List;

public record FindAllMemberBadgesResponse(int badgeCount, List<AcquireBadge> badges) {

    public static FindAllMemberBadgesResponse from(List<MemberBadge> findBadges) {
        return new FindAllMemberBadgesResponse(
                findBadges.size(),
                findBadges.stream()
                        .map(findBadge ->
                                new AcquireBadge(
                                        findBadge.getId(),
                                        findBadge.getBadge().getFileName(),
                                        findBadge.getBadge().getName(),
                                        findBadge.getBadge().getDescription()
                                )
                        )
                        .toList()
        );
    }

    public record AcquireBadge(
            Long id,
            int fileName,
            String name,
            String description
    ) {
    }
}
