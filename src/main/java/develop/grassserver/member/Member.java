package develop.grassserver.member;

import develop.grassserver.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE member SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Member extends BaseEntity {

    @Column(length = 8, nullable = false, unique = true)
    private String name;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    @Embedded
    private Major major;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Profile profile;

    @Embedded
    private StudyRecord studyRecord;

    public boolean isMyName(String otherName) {
        return this.name.equals(otherName);
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
