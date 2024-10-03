package develop.grassserver.member.memberGrass;

import develop.grassserver.member.memberGrass.dto.MemberStreakResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버 스트릭 관련 조회 APIs", description = "현재 스트릭, 최장 스트릭, 연간 잔디 등을 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/{id}/grass")
public class MemberGrassController {
    private final MemberGrassService memberGrassService;

    ResponseEntity<MemberStreakResponse> getMemberStreak(@PathVariable("id") Long memberId) {
        return ResponseEntity.ok(memberGrassService.getMemberStreak(memberId));
    }
}
