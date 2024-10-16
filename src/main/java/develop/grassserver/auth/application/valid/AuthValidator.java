package develop.grassserver.auth.application.valid;

import develop.grassserver.member.presentation.dto.CheckAuthCodeRequest;
import java.util.Objects;

public final class AuthValidator {

    private  AuthValidator() {
    }

    public static boolean isCorrectAuthCode(String code, CheckAuthCodeRequest request) {
        return Objects.requireNonNull(code)
                .equals(request.code());
    }
}
