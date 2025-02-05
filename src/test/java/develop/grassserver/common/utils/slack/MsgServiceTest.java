package develop.grassserver.common.utils.slack;

import develop.grassserver.common.message.SlackMsgService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsgServiceTest {

    @Autowired
    private SlackMsgService slackMsgService;

    @Test
    void 메시지_전송_테스트() {
        String scheduleName = "잔디 점수 계산 스케줄링";
        LocalDateTime time = LocalDateTime.now();
        Exception exception = new RuntimeException("Database connection timeout");

        slackMsgService.sendSchedulingError(scheduleName, time, exception);
    }

}