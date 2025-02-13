package develop.grassserver.common.annotation;

import jakarta.persistence.PersistenceException;
import jakarta.persistence.QueryTimeoutException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@Retryable(
        retryFor = {
                PersistenceException.class,
                SQLException.class,
                SQLTimeoutException.class,
                QueryTimeoutException.class,
                JDBCConnectionException.class
        },
        maxAttempts = 5,
        backoff = @Backoff(delay = 5000)
)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RDBRetryable {
}
