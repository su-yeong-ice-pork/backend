package develop.grassserver.member.check;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class MemberValidator {

    private static final int MAX_NAME_LENGTH = 16;

    private MemberValidator() {
    }

    public static boolean isCorrectNameFormat(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            log.error("이름은 16글자 이하이어야 합니다.");
            return false;
        }
        return true;
    }
}
