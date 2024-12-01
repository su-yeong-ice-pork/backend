package develop.grassserver.notification.domain.entity;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.notification.presentation.dto.FindAllEmojiAndMessageNotificationsResponse.NotificationDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageNotification extends Notification {

    @Column(length = 20, nullable = false)
    private String message;

    public MessageNotification(Member sender, Member receiver, String message) {
        super(sender, receiver);
        this.message = message;
    }

    @Override
    public NotificationDTO toDTO(boolean isToday, String time, String date) {
        return new NotificationDTO(
                getId(),
                getSender().getName(),
                "message",
                -1,
                message,
                isToday,
                time,
                date
        );
    }
}
