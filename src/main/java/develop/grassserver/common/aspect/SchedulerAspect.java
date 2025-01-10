package develop.grassserver.common.aspect;

import develop.grassserver.common.annotation.SchedulerName;
import develop.grassserver.common.message.SlackMsgService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SchedulerAspect {

    private final SlackMsgService slackMsgService;

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledMethods() {
    }

    @Around("scheduledMethods() && @annotation(schedulerName)")
    public Object aroundScheduledMethods(ProceedingJoinPoint joinPoint, SchedulerName schedulerName) throws Throwable {
        String schedulerNameValue = schedulerName.value();

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.error("{} 실행 중 오류 발생: {}", schedulerNameValue, e.getMessage(), e);
            slackMsgService.sendSchedulingError(schedulerNameValue, LocalDateTime.now(), e);

            throw e;
        }
    }
}
