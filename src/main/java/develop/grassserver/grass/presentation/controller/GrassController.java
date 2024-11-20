package develop.grassserver.grass.presentation.controller;

import develop.grassserver.grass.application.service.GrassService;
import develop.grassserver.grass.presentation.dto.AttendanceResponse;
import develop.grassserver.grass.presentation.dto.StudyTimeRequest;
import develop.grassserver.grass.presentation.dto.StudyTimeResponse;
import develop.grassserver.grass.application.service.MemberGrassService;
import develop.grassserver.common.utils.ApiUtils;
import develop.grassserver.common.utils.ApiUtils.ApiResult;
import develop.grassserver.common.annotation.LoginMember;
import develop.grassserver.member.domain.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "스트릭(잔디) APIs", description = "공부시간 등록/조회, 잔디 조회, 출석 등을 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grass")
public class GrassController {
    
    private final GrassService grassService;
    private final MemberGrassService memberGrassService;

    @Operation(summary = "출석 인증(잔디 생성) API", description = "인증 절차 후에 호출하는 잔디 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "저장 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패, 재로그인 필요"),
            @ApiResponse(responseCode = "400", description = "이미 출석을 완료함")
    })
    @PostMapping
    public ResponseEntity<ApiResult<String>> createAttendance(@LoginMember Member member) {
        memberGrassService.createAttendance(member);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }

    @Operation(summary = "공부시간 조회 API", description = "기록장 타이머 공부시간 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공. 응답 에러 코드는 무시하셈"),
            @ApiResponse(responseCode = "401", description = "인증 실패, 재로그인 필요"),
            @ApiResponse(responseCode = "400", description = "출석 기록이 없음")
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
            @ApiResponse(responseCode = "400", description = "출석 기록이 없음")
    })
    @PatchMapping("/study-time")
    public ResponseEntity<ApiResult<StudyTimeResponse>> updateStudyRecord(
            @LoginMember Member member,
            @Valid @RequestBody StudyTimeRequest request
    ) {
        StudyTimeResponse response = grassService.updateStudyRecord(member, request);
        return ResponseEntity.ok(ApiUtils.success(response));
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
