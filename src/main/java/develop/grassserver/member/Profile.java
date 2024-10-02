package develop.grassserver.member;

import develop.grassserver.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends BaseEntity {

    @Column(length = 1024, nullable = false)
    private String image;

    @Column(length = 100)
    private String message;
}
