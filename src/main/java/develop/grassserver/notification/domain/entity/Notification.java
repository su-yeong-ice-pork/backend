package develop.grassserver.notification.domain.entity;

import develop.grassserver.common.BaseEntity;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.notification.presentation.dto.FindAllEmojiAndMessageNotificationsResponse.NotificationDTO;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member receiver;

    public abstract NotificationDTO toDTO(boolean isToday, String time, String date);
}
