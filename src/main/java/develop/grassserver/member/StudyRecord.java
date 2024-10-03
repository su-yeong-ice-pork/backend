package develop.grassserver.member;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyRecord {

    private LocalTime totalStudyTime;
    private String topStreak;

    public void updateStudyRecord() {

    }
}
