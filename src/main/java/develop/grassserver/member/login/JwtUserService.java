package develop.grassserver.member.login;

import develop.grassserver.member.Member;
import develop.grassserver.member.MemberRepository;
import develop.grassserver.member.exception.ForbiddenException;
import develop.grassserver.utils.jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserService {
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final MemberService memberService;

    public JwtUserService(MemberRepository memberRepository, JwtService jwtService, MemberService memberService) {
        this.memberRepository = memberRepository;
        this.jwtService = jwtService;
        this.memberService = memberService;
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new EntityNotFoundException("Member"));
        if (!member.isPasswordCorrect(loginRequest.password())) {
            throw new ForbiddenException("로그인 실패: 비밀번호 불일치");
        }
        return jwtService.createToken(member.getId());
    }

    @Transactional(readOnly = true)
    public String getToken(Long userId) {
        return jwtService.createToken(userId);
    }
}
