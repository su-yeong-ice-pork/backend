package develop.grassserver.member.check;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberValidatorTest {

    @Test
    @DisplayName("성공 - 멤버 아이디 길이가 정상적인 경우")
    void test1() {
        String memberId = "abcde";

        assertThat(
                MemberValidator.isCorrectLoginIdFormat(memberId))
                .isTrue();
    }

    @Test
    @DisplayName("실패 - 멤버 ID 길이가 4 미만인 경우")
    void test2() {
        String memberId = "abc";

        assertThat(
                MemberValidator.isCorrectLoginIdFormat(memberId))
                .isFalse();
    }

    @Test
    @DisplayName("실패 - 멤버 ID 길이가 10 초과인 경우")
    void test3() {
        String memberId = "abcdefg123456";

        assertThat(
                MemberValidator.isCorrectLoginIdFormat(memberId))
                .isFalse();
    }

    @Test
    @DisplayName("성공 - 멤버 ID 형식이 올바른 경우")
    void test4() {
        String memberId = "aaaa";

        assertThat(
                MemberValidator.isCorrectLoginIdFormat(memberId))
                .isTrue();
    }

    @Test
    @DisplayName("실패 - 멤버 ID 형식이 올바르지 않은 경우: 공백 포함")
    void test5() {
        String memberId = "aa aa";

        assertThat(
                MemberValidator.isCorrectLoginIdFormat(memberId))
                .isFalse();
    }

    @Test
    @DisplayName("실패 - 멤버 ID 형식이 올바르지 않은 경우: 한글 포함")
    void test6() {
        String memberId = "aa잔디";

        assertThat(
                MemberValidator.isCorrectLoginIdFormat(memberId))
                .isFalse();
    }
}
