package develop.grassserver.member.domain.entity;

import develop.grassserver.common.BaseEntity;
import develop.grassserver.profile.domain.entity.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SQLDelete(sql = "UPDATE member SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Member extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
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

    public void updateProfileImage(String url) {
        profile.updateImage(url);
    }

    public void updateBannerImage(String url) {
        profile.updateMainBanner(url);
    }

    public void updateProfileMessage(String message) {
        profile.updateMessage(message);
    }

    public void deleteEmailAndName() {
        this.email = email + "_deleted" + this.getId();
        this.name = name + "_deleted" + this.getId();
        delete();
    }
}
