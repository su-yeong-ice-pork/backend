package develop.grassserver.notification.domain.entity;

import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmojiNotification extends Notification {

    @Column(nullable = false)
    private int emojiNumber;

    public EmojiNotification(Member sender, Member receiver, int emojiNumber) {
        super(sender, receiver);
        this.emojiNumber = emojiNumber;
    }
}
