package develop.grassserver.grass;

import develop.grassserver.grass.dto.StudyTimeRequest;
import develop.grassserver.grass.dto.StudyTimeResponse;
import develop.grassserver.member.Member;
import develop.grassserver.utils.ApiUtils;
import develop.grassserver.utils.ApiUtils.ApiResult;
import develop.grassserver.utils.annotation.LoginMember;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "스트릭(잔디) APIs", description = "공부시간 등록/조회, 잔디 조회, 출석 등을 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grass")
public class GrassController {
    private final GrassService grassService;

    @GetMapping("study-time")
    public ResponseEntity<ApiResult<StudyTimeResponse>> getStudyRecord(@LoginMember Member member) {
        StudyTimeResponse studyRecord = grassService.getStudyRecord(member);
        return ResponseEntity.ok(ApiUtils.success(studyRecord));
    }

    @PatchMapping("study-time")
    public ResponseEntity<ApiResult<String>> updateStudyRecord(@LoginMember Member member,
                                                               @Valid StudyTimeRequest request) {
        grassService.updateStudyRecord(member, request);
        return ResponseEntity.ok(ApiUtils.success());
    }

}
