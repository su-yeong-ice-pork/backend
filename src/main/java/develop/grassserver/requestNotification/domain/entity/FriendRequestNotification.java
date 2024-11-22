package develop.grassserver.requestNotification.domain.entity;

import develop.grassserver.friend.domain.entity.Friend;
import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE request_notification SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class FriendRequestNotification extends RequestNotification {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Friend friend;

    public FriendRequestNotification(Member sender, Member receiver, Friend friend) {
        super(sender, receiver);
        this.friend = friend;
    }
}
