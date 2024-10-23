package develop.grassserver.member.profile.banner;

import develop.grassserver.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    Optional<Banner> findByMember(Member member);
}