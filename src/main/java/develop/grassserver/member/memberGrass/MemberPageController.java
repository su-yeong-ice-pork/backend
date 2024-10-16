package develop.grassserver.member.memberGrass;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "멤버 스트릭 관련 조회 APIs", description = "스트릭, 연간 잔디 등을 담당하는 APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/my-page")
public class MemberPageController {
    private final MemberGrassService memberGrassService;


}
