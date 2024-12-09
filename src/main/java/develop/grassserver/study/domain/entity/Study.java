package develop.grassserver.study.domain.entity;

import develop.grassserver.common.BaseEntity;
import develop.grassserver.member.domain.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE study SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Study extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String goalMessage;

    @ColumnDefault("0")
    private long goalTime = 0;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Duration totalStudyTime = Duration.ZERO;

    @Column(unique = true, nullable = false)
    private String inviteCode;

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyMember> members = new ArrayList<>();

    @Builder
    public Study(String name, String goalMessage, long goalTime, String inviteCode) {
        this.name = name;
        this.goalMessage = goalMessage;
        this.goalTime = goalTime;
        this.inviteCode = inviteCode;
    }

    public void addMember(Member member, StudyRole role) {
        boolean isMemberAlreadyAdded = members.stream()
                .anyMatch(studyMember -> studyMember.getMember().getId().equals(member.getId()));

        if (!isMemberAlreadyAdded) {
            StudyMember studyMember = new StudyMember(member, this, role);
            this.members.add(studyMember);
        }
    }
}
