package develop.grassserver.utils.duration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Duration;

@Converter(autoApply = true)
public class DurationAttributeConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return (duration == null) ? 0L : duration.getSeconds();
    }

    @Override
    public Duration convertToEntityAttribute(Long seconds) {
        return Duration.ofSeconds(seconds);
    }
}
