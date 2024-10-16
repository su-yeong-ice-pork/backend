package develop.grassserver.badge.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBadgeRepository extends JpaRepository<MemberBadge, Long> {

    List<MemberBadge> findAllByMemberId(Long memberId);
}
