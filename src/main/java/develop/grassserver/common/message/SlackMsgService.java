package develop.grassserver.common.message;

import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SlackMsgService {

    @Value("${slack.webhookUrl}")
    private String webhookUrl;

    public void sendSchedulingError(String scheduleName, LocalDateTime time, Exception exception) {
        String message = formatSchedulingErrorMessage(scheduleName, time, exception);

        sendSlackMessage(message);
    }

    private String formatSchedulingErrorMessage(String scheduleName, LocalDateTime time, Exception exception) {
        String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return String.format(
                "🚨 *스케줄링 오류 발생*\n" +
                        "스케줄링 이름: `%s`\n" +
                        "발생 시간: `%s`\n" +
                        "오류 원인: `%s`",
                scheduleName.replace("\"", "\\\""),
                formattedTime,
                exception.getMessage().replace("\"", "\\\"")
        );
    }

    private void sendSlackMessage(String message) {
        Slack slack = Slack.getInstance();
        String payload = String.format("{\"text\":\"%s\"}", message);

        try {
            WebhookResponse response = slack.send(webhookUrl, payload);
            log.info("슬랙 메시지 전송 완료: {}", response.getBody());
        } catch (IOException e) {
            log.error("슬랙 메시지 발송 중 문제가 발생했습니다: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}