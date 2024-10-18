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
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final RedisService redisService;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

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
            message.setSubject("[잔디] 인증코드 발송 메일입니다.");
            message.setFrom("grass-authorization@naver.com");

            Context context = new Context();
            context.setVariable("code", code);

            String htmlContent = templateEngine.process("mail", context);

            message.setContent(htmlContent, "text/html; charset=utf-8");
            return message;
        } catch (MessagingException e) {
            log.error("메일 전송에 실패하였습니다.");
            throw new MailSendException("메일 전송에 실패하였습니다.");
        }
    }
}
