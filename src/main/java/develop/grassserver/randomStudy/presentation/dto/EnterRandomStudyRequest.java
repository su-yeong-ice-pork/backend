package develop.grassserver.randomStudy.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record EnterRandomStudyRequest(
        @NotNull(message = "출석 시간은 필수입니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime attendanceTime,

        @NotNull(message = "출석 날짜는 필수입니다.")
        @FutureOrPresent(message = "출석 날짜는 과거일 수 없습니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate attendanceDate) {
}
