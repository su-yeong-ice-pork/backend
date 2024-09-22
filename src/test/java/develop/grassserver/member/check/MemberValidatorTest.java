package develop.grassserver.member.check;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberValidatorTest {

    @Test
    @DisplayName("성공 - 멤버 이름 길이가 정상인 경우")
    void test7() {
        String name = "aaaa";

        assertThat(
                MemberValidator.isCorrectNameFormat(name))
                .isTrue();
    }

    @Test
    @DisplayName("실패 - 멤버 이름 길이가 16글자를 초과하는 경우")
    void test8() {
        String name = "aaaaaaaaaaaaaaaaa";

        assertThat(
                MemberValidator.isCorrectNameFormat(name))
                .isFalse();
    }
}
