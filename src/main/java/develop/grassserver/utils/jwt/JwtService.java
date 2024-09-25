package develop.grassserver.utils.jwt;


import static develop.grassserver.utils.jwt.JwtUtil.*;

import develop.grassserver.member.Member;
import develop.grassserver.member.login.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final MemberService memberService;

    public JwtService(MemberService memberService) {
        this.memberService = memberService;
    }

    public String createToken(Long id) {
        return Jwts.builder()
                .subject(id.toString())
                .issuer(ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Member getLoginUser(String token) {
        Long id = getIdFromToken(token);
        return memberService.findUser(id);
    }

    private Long getIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(removeBearerPrefix(token))
                .getPayload();
        return Long.parseLong(claims.getSubject());
    }

    private String removeBearerPrefix(String token) {
        if (!token.startsWith(TOKEN_PREFIX)) {
            throw new SecurityException();
        }
        return token.substring(TOKEN_BEGIN_INDEX);
    }
}
