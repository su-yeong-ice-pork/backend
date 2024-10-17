package develop.grassserver.auth.application.service;

import static develop.grassserver.common.utils.jwt.JwtUtil.TOKEN_PREFIX;

import develop.grassserver.auth.presentation.dto.LoginRequest;
import develop.grassserver.auth.presentation.dto.RefreshTokenDTO;
import develop.grassserver.auth.presentation.dto.TokenDTO;
import develop.grassserver.member.application.exception.InvalidPasswordException;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.member.infrastructure.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private TokenDTO createToken(String email) {
        TokenDTO token = jwtService.createAllToken(email);
        jwtService.saveRefreshToken(email, TOKEN_PREFIX + token.refreshToken());
        return token;
    }

    public TokenDTO login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new EntityNotFoundException("Member"));

        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
            throw new InvalidPasswordException();
        }
        
        return createToken(member.getEmail());
    }

    public TokenDTO autoLogin(RefreshTokenDTO refreshTokenDTO) {
        return jwtService.renewTokens(refreshTokenDTO.refreshToken());
    }
}