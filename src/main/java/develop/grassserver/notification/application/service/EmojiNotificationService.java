package develop.grassserver.notification.application.service;

import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.notification.domain.entity.EmojiNotification;
import develop.grassserver.notification.infrastructure.repository.EmojiNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmojiNotificationService {

    private final EmojiNotificationRepository emojiNotificationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveEmojiNotification(Member me, Member other, int emojiNumber) {
        EmojiNotification emojiNotification = createEmojiNotification(me, other, emojiNumber);
        emojiNotificationRepository.save(emojiNotification);
    }

    private EmojiNotification createEmojiNotification(Member me, Member other, int emojiNumber) {
        return new EmojiNotification(me, other, emojiNumber);
    }
}
