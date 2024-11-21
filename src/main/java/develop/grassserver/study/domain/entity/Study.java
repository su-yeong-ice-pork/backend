package develop.grassserver.study.domain.entity;

import develop.grassserver.common.BaseEntity;
import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE study SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Study extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String goalMessage;

    @ColumnDefault("0")
    @Builder.Default
    private long goalTime = 0;

    @ColumnDefault("0")
    @Builder.Default
    @Column(nullable = false)
    private Duration totalStudyTime = Duration.ZERO;

    private String inviteCode;

    @OneToMany(mappedBy = "study")
    @Builder.Default
    private List<StudyMember> members = new ArrayList<>();

    public void addMember(Member member, StudyRole role) {
        StudyMember studyMember = new StudyMember(member, this, role);
        this.members.add(studyMember);
    }
}
