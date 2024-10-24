package develop.grassserver.common.security;

import static develop.grassserver.common.security.JwtErrorMessages.EXPIRED_TOKEN;
import static develop.grassserver.common.security.JwtErrorMessages.ILLEGAL_ARGUMENT;
import static develop.grassserver.common.security.JwtErrorMessages.INVALID_SIGNATURE;
import static develop.grassserver.common.security.JwtErrorMessages.INVALID_TOKEN;
import static develop.grassserver.common.security.JwtErrorMessages.MALFORMED_TOKEN;
import static develop.grassserver.common.security.JwtErrorMessages.SERVER_ERROR;
import static develop.grassserver.common.security.JwtErrorMessages.UNSUPPORTED_TOKEN;

import com.fasterxml.jackson.databind.ObjectMapper;
import develop.grassserver.common.utils.ApiUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            handleException(request, response, ex);
        }
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception ex)
            throws IOException {
        ApiUtils.ApiResult<?> apiResult;
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        if (ex instanceof ExpiredJwtException) {
            apiResult = ApiUtils.error(status, EXPIRED_TOKEN.getMessage());
        } else if (ex instanceof UnsupportedJwtException) {
            apiResult = ApiUtils.error(status, UNSUPPORTED_TOKEN.getMessage());
        } else if (ex instanceof MalformedJwtException) {
            apiResult = ApiUtils.error(status, MALFORMED_TOKEN.getMessage());
        } else if (ex instanceof SignatureException || ex instanceof SecurityException) {
            apiResult = ApiUtils.error(status, INVALID_SIGNATURE.getMessage());
        } else if (ex instanceof IllegalArgumentException) {
            apiResult = ApiUtils.error(status, ILLEGAL_ARGUMENT.getMessage());
        } else if (ex instanceof JwtException) {
            apiResult = ApiUtils.error(status, INVALID_TOKEN.getMessage());
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            apiResult = ApiUtils.error(status, SERVER_ERROR.getMessage());
        }

        log.error("Security Exception 발생: [{}] {}", request.getRequestURI(), ex.getMessage());

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = objectMapper.writeValueAsString(apiResult);
        response.getWriter().write(jsonResponse);
    }

}