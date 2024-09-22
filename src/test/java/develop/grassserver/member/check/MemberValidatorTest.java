package develop.grassserver.member.check;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberValidatorTest {

    @Test
    @DisplayName("성공 - 멤버 이름 길이가 정상인 경우")
    void test1() {
        String name = "aaaa";

        assertThat(
                MemberValidator.isCorrectNameFormat(name))
                .isTrue();
    }

    @Test
    @DisplayName("실패 - 멤버 이름 길이가 16글자를 초과하는 경우")
    void test2() {
        String name = "aaaaaaaaaaaaaaaaa";

        assertThat(
                MemberValidator.isCorrectNameFormat(name))
                .isFalse();
    }

    @Test
    @DisplayName("성공 - 멤버 이름 양식이 올바른 경우")
    void test3() {
        String name = "김김진진우우";

        assertThat(
                MemberValidator.isCorrectNameFormat(name))
                .isTrue();
    }

    @Test
    @DisplayName("실패 - 멤버 이름 양식이 올바르지 않은 경우1")
    void test4() {
        String name = "김김진진우우  ";

        assertThat(
                MemberValidator.isCorrectNameFormat(name))
                .isFalse();
    }

    @Test
    @DisplayName("실패 - 멤버 이름 양식이 올바르지 않은 경우2")
    void test5() {
        String name = "김김진진우우!!";

        assertThat(
                MemberValidator.isCorrectNameFormat(name))
                .isFalse();
    }
}
