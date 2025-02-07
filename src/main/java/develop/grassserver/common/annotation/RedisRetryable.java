package develop.grassserver.common.annotation;

import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Retryable(
        retryFor = {
                RedisConnectionFailureException.class,
                RedisConnectionException.class,
                RedisCommandTimeoutException.class,
                RedisException.class,
                SocketTimeoutException.class,
                ConnectException.class
        },
        maxAttempts = 5,
        backoff = @Backoff(delay = 5000)
)
public @interface RedisRetryable {
}
