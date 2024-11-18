package develop.grassserver.requestNotification.domain.entity;

import develop.grassserver.friend.domain.entity.Friend;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendRequestNotification extends RequestNotification {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Friend friend;
}
