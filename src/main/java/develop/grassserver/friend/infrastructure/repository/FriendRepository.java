package develop.grassserver.friend.infrastructure.repository;

import develop.grassserver.friend.domain.entity.Friend;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("select f "
            + "from Friend f "
            + "where (f.member1.id = :member1Id and f.member2.id = :member2Id) "
            + "or (f.member1.id = :member2Id and f.member2.id = :member1Id)")
    Optional<Friend> findFriend(@Param("member1Id") Long member1Id, @Param("member2Id") Long member2Id);

    @Query("select f "
            + "from Friend f "
            + "where f.requestStatus = 'ACCEPTED' "
            + "and (f.member1.id = :memberId or f.member2.id = :memberId)")
    List<Friend> findAllMyFriends(@Param("memberId") Long memberId);
}
