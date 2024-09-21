package develop.grassserver.member.check;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import develop.grassserver.member.Member;
import develop.grassserver.member.MemberRepository;
import develop.grassserver.member.exception.DuplicateMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberCheckServiceTest {

    @Autowired
    MemberCheckService memberCheckService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("실패 - 멤버 ID 중복인 경우")
    void test1() {
        Member member = Member.builder()
                .name("aaaa")
                .email("aaaa@pusan.ac.kr")
                .loginId("aaaa")
                .password("aaaa")
                .build();
        memberRepository.save(member);

        String newMemberID = "aaaa";
        assertThatThrownBy(
                () -> memberCheckService.checkMemberId(newMemberID))
                .isInstanceOf(DuplicateMemberException.class);
    }
}
