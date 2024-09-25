package develop.grassserver;

import develop.grassserver.utils.ApiUtils;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 입력값 유효성 검사 -> 여러 개의 에러 메세지를 한번에 반환
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiUtils.ApiResult<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 첫 번째 에러 메시지만 추출
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid input");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiUtils.error(HttpStatus.BAD_REQUEST, errorMessage));
    }
}
