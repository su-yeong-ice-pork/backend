package develop.grassserver.member.application.valid;

import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class MemberValidator {

    private static final int MAX_NAME_LENGTH = 8;

    private static final Pattern NAME = Pattern.compile("^[가-힣a-zA-Z0-9]+$");
    private static final Pattern EMAIL = Pattern.compile("^[a-zA-Z0-9._%+-]+@pusan\\.ac\\.kr$");

    private MemberValidator() {
    }

    public static boolean isCorrectNameFormat(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            log.error("이름은 " + MAX_NAME_LENGTH + "글자 이하이어야 합니다.");
            return false;
        }
        if (!NAME.matcher(name).matches()) {
            log.error("이름은 한글, 영어, 숫자만 포함할 수 있습니다.");
            return false;
        }
        return true;
    }

    public static boolean isCorrectEmailFormat(String email) {
        if (!EMAIL.matcher(email).matches()) {
            log.error("이메일은 부산대학교 Gmail 을 사용하여야 합니다.");
            return false;
        }
        return true;
    }
}
