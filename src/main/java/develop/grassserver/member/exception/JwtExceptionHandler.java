package develop.grassserver.member.exception;

import develop.grassserver.utils.ApiUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JwtExceptionHandler {

    private ResponseEntity<ApiUtils.ApiResult<String>> createJwtErrorResponse(String errorMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("WWW-Authenticate", "Bearer realm=\"access\"");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .headers(headers)
                .body(ApiUtils.error(HttpStatus.UNAUTHORIZED, errorMessage));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiUtils.ApiResult<String>>  handleExpiredJwtException(ExpiredJwtException ex) {
        return createJwtErrorResponse("토큰이 만료되었습니다.");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ApiUtils.ApiResult<String>>  handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return createJwtErrorResponse("지원하지 않는 토큰 형식입니다.");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiUtils.ApiResult<String>>  handleMalformedJwtException(MalformedJwtException ex) {
        return createJwtErrorResponse("토큰의 형식이 올바르지 않습니다.");
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiUtils.ApiResult<String>>  handleSecurityException(SecurityException ex) {
        return createJwtErrorResponse("토큰의 서명이 유효하지 않습니다.");
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiUtils.ApiResult<String>>  handleException(Exception ex) {
        return createJwtErrorResponse("유효하지 않은 토큰입니다.");
    }
}
