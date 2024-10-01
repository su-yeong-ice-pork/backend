package develop.grassserver.member.login;

import develop.grassserver.member.Member;
import develop.grassserver.member.MemberRepository;
import develop.grassserver.member.exception.InvalidPasswordException;
import develop.grassserver.utils.jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtUserService {
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new EntityNotFoundException("Member"));
        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
            throw new InvalidPasswordException();
        }
        return jwtService.createToken(member.getEmail());
    }

    public String getToken(String email) {
        return jwtService.createToken(email);
    }
}
