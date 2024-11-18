package develop.grassserver.friend.application.service;

import develop.grassserver.friend.domain.entity.Friend;
import develop.grassserver.friend.domain.entity.FriendRequestStatus;
import develop.grassserver.friend.infrastructure.repository.FriendRepository;
import develop.grassserver.friend.presentation.dto.RequestFriendRequest;
import develop.grassserver.friend.application.exception.ExistFriendRelationException;
import develop.grassserver.member.application.service.MemberService;
import develop.grassserver.member.domain.entity.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final MemberService memberService;
    private final FriendRepository friendRepository;

    @Transactional
    public void requestFriend(Member member, RequestFriendRequest request) {
        Member me = memberService.findMemberById(member.getId());
        Member other = memberService.findMemberById(request.memberId());

        checkExistFriendRelation(me, other);

        Friend friend = createFriendRelation(me, other);
        friendRepository.save(friend);
    }

    private void checkExistFriendRelation(Member me, Member other) {
        Optional<Friend> optionalFriend = friendRepository.findFriend(me.getId(), other.getId());
        if (optionalFriend.isPresent()) {
            throw new ExistFriendRelationException();
        }
    }

    private Friend createFriendRelation(Member me, Member other) {
        return Friend.builder()
                .member1(me)
                .member2(other)
                .requestStatus(FriendRequestStatus.PENDING)
                .build();
    }
}
