package develop.grassserver.member.badge;

import develop.grassserver.member.badge.dto.FindAllMemberBadgesResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeService {

    private final MemberBadgeRepository memberBadgeRepository;

    public FindAllMemberBadgesResponse findAllMemberBadges(Long memberId) {
        List<MemberBadge> badges = memberBadgeRepository.findAllByMemberId(memberId);
        return FindAllMemberBadgesResponse.from(badges);
    }
}
