package develop.grassserver.member.profile.image;

import develop.grassserver.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByMember(Member member);
}
