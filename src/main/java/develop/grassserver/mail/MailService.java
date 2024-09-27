package develop.grassserver.mail;

import develop.grassserver.mail.exeption.MailSendException;
import develop.grassserver.member.auth.RedisService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final RedisService redisService;
    private final JavaMailSender javaMailSender;

    public void sendMail(String email) {
        String code = createAuthorizationCode();
        MimeMessage message = createMailMessage(email, code);
        javaMailSender.send(message);
        redisService.saveAuthCode(email, code);
    }

    private String createAuthorizationCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    private MimeMessage createMailMessage(String email, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.addRecipients(Message.RecipientType.TO, email);
            message.setSubject("Grass 인증 번호입니다.");
            message.setText("이메일 인증코드: " + code);
            message.setFrom("grass-authorization@naver.com");
            return message;
        } catch (MessagingException e) {
            log.error("메일 전송에 실패하였습니다.");
            throw new MailSendException("메일 전송에 실패하였습니다.");
        }
    }
}
