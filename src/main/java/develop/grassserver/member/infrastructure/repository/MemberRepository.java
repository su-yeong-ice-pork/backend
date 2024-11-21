package develop.grassserver.member.infrastructure.repository;

import develop.grassserver.member.domain.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByName(String name);

    boolean existsByEmail(String email);
  
    Optional<Member> findByEmail(String email);

    @Query("select m from Member m join fetch m.profile where m.name = :name")
    Optional<Member> findByNameWithProfile(@Param("name") String name);

    @Query("select m from Member m join fetch m.profile where m.email = :email")
    Optional<Member> findByEmailWithProfile(@Param("email") String email);

    @Query("select m from Member m join fetch m.profile where m.id = :id")
    Optional<Member> findByIdWithProfile(@Param("id") Long id);

    @Query("select m from Member m join fetch m.profile where m.id in :ids")
    List<Member> findAllByIdsWithProfile(@Param("ids") List<Long> ids);
}
