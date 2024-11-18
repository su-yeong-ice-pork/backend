package develop.grassserver.notification.domain.entity;

import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageNotification extends Notification {

    @Column(length = 20, nullable = false)
    private String message;

    public MessageNotification(Member sender, Member receiver, String message) {
        super(sender, receiver);
        this.message = message;
    }
}
