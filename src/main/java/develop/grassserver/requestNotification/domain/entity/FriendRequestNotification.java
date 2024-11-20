package develop.grassserver.requestNotification.domain.entity;

import develop.grassserver.friend.domain.entity.Friend;
import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestNotification extends RequestNotification {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Friend friend;

    public FriendRequestNotification(Member sender, Member receiver, Friend friend) {
        super(sender, receiver);
        this.friend = friend;
    }
}
