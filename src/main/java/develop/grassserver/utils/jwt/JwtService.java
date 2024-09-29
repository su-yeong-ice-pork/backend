package develop.grassserver.utils.jwt;


import static develop.grassserver.utils.jwt.JwtUtil.ISSUER;
import static develop.grassserver.utils.jwt.JwtUtil.SECRET_KEY;
import static develop.grassserver.utils.jwt.JwtUtil.TOKEN_BEGIN_INDEX;
import static develop.grassserver.utils.jwt.JwtUtil.TOKEN_PREFIX;
import static develop.grassserver.utils.jwt.JwtUtil.expirationSeconds;

import develop.grassserver.member.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JwtService {
    private final MemberService memberService;

    public JwtService(MemberService memberService) {
        this.memberService = memberService;
    }

    public String createToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuer(ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                .signWith(SECRET_KEY)
                .compact();
    }

//    public Member getLoginUser(String token) {
//        Long id = getIdFromToken(token);
//        return memberService.findUser(id);
//    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(removeBearerPrefix(token))
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(removeBearerPrefix(token));
            return true;
        } catch (SecurityException | io.jsonwebtoken.MalformedJwtException | io.jsonwebtoken.ExpiredJwtException |
                 io.jsonwebtoken.UnsupportedJwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_BEGIN_INDEX);
        }
        return null;
    }

    private String removeBearerPrefix(String token) {
        if (!token.startsWith(TOKEN_PREFIX)) {
            throw new SecurityException();
        }
        return token.substring(TOKEN_BEGIN_INDEX);
    }
}
