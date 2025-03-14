package develop.grassserver.profile.domain.entity;

import develop.grassserver.profile.application.exception.NotEnoughFreezeCountException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Freeze {

    private int freezeCount;

    public Freeze() {
        this.freezeCount = 0;
    }

    public void decrease() {
        if (this.freezeCount > 0) {
            this.freezeCount--;
        }
        throw new NotEnoughFreezeCountException();
    }
}
