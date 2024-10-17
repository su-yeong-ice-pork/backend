package develop.grassserver.profile.infrastructure.repository;


import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.profile.domain.entity.image.Image;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByMember(Member member);
}
