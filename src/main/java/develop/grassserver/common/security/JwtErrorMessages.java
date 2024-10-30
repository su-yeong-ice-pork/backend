package develop.grassserver.common.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtErrorMessages {

    EXPIRED_TOKEN("토큰이 만료되었습니다."),
    UNSUPPORTED_TOKEN("지원하지 않는 토큰 형식입니다."),
    MALFORMED_TOKEN("토큰의 형식이 올바르지 않습니다."),
    INVALID_SIGNATURE("토큰의 서명이 유효하지 않습니다."),
    ILLEGAL_ARGUMENT("토큰이 제공되지 않았습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    SERVER_ERROR("서버 오류가 발생했습니다.");

    private final String message;
}
