package develop.grassserver.member;

import develop.grassserver.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Member extends BaseEntity {

    @Column(length = 16, nullable = false, unique = true)
    private String name;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 10, nullable = false, unique = true)
    private String loginId;

    @Column(length = 64, nullable = false)
    private String password;
}
