package develop.grassserver.member.check;

import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class MemberValidator {

    private static final int MIN_LOGIN_ID_LENGTH = 4;
    private static final int MAX_LOGIN_ID_LENGTH = 10;

    private static final Pattern LOGIN_ID = Pattern.compile(
            "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]+$");

    private MemberValidator() {
    }

    public static boolean isCorrectLoginIdFormat(String memberId) {
        if (memberId.length() < MIN_LOGIN_ID_LENGTH || memberId.length() > MAX_LOGIN_ID_LENGTH) {
            log.error("ID는 4글자 이상 10글자 이하이어야 합니다.");
            return false;
        }
        if (!LOGIN_ID.matcher(memberId).matches()) {
            log.error("ID 형식이 잘못되었습니다. ID는 영어 대소문자, 숫자, 기호로만 구성될 수 있습니다.");
            return false;
        }
        return true;
    }
}
