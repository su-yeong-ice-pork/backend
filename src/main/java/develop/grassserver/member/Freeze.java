package develop.grassserver.member;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Freeze {

    protected Freeze() {
        this.freezeCount = 0;
    }

    private int freezeCount;
}
