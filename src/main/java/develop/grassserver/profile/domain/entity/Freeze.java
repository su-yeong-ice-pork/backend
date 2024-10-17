package develop.grassserver.profile.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Freeze {

    public Freeze() {
        this.freezeCount = 0;
    }

    private int freezeCount;
}
