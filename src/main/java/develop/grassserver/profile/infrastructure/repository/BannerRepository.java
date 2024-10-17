package develop.grassserver.profile.infrastructure.repository;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.profile.domain.entity.banner.Banner;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    Optional<Banner> findByMember(Member member);
}
