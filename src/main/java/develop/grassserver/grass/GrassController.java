package develop.grassserver.grass;

import develop.grassserver.grass.dto.AttendanceResponse;
import develop.grassserver.grass.dto.StudyTimeRequest;
import develop.grassserver.grass.dto.StudyTimeResponse;
import develop.grassserver.member.Member;
import develop.grassserver.utils.ApiUtils;
import develop.grassserver.utils.ApiUtils.ApiResult;
import develop.grassserver.utils.annotation.LoginMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "스트릭(잔디) APIs", description = "공부시간 등록/조회, 잔디 조회, 출석 등을 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grass")
public class GrassController {
    private final GrassService grassService;

    @Operation(summary = "공부시간 조회 API", description = "기록장 타이머 공부시간 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패, 재로그인 필요"),
            @ApiResponse(responseCode = "404", description = "출석 기록이 없음")
    })
    @GetMapping("/study-time")
    public ResponseEntity<ApiResult<StudyTimeResponse>> getStudyRecord(@LoginMember Member member) {
        StudyTimeResponse studyRecord = grassService.getStudyRecord(member);
        return ResponseEntity.ok(ApiUtils.success(studyRecord));
    }

    @Operation(summary = "공부시간 저장 API", description = "기록장 타이머 공부시간 저장 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "저장 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패, 재로그인 필요"),
            @ApiResponse(responseCode = "404", description = "출석 기록이 없음")
    })
    @PatchMapping("/study-time")
    public ResponseEntity<ApiResult<String>> updateStudyRecord(@LoginMember Member member,
                                                               @Valid @RequestBody StudyTimeRequest request) {
        grassService.updateStudyRecord(member, request);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @Operation(summary = "출석 확인 API", description = "공부 시간 저장 전 출석 여부 확인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "저장 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패, 재로그인 필요")
    })
    @GetMapping("/attendance/today")
    public ResponseEntity<ApiResult<AttendanceResponse>> getAttendance(@LoginMember Member member) {
        AttendanceResponse response = grassService.getAttendance(member);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

}
