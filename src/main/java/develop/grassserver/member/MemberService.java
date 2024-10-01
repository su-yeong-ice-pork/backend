package develop.grassserver.member;

import develop.grassserver.mail.MailService;
import develop.grassserver.member.dto.ChangePasswordRequest;
import develop.grassserver.member.dto.MemberAuthRequest;
import develop.grassserver.member.dto.MemberJoinRequest;
import develop.grassserver.member.dto.MemberJoinSuccessResponse;
import develop.grassserver.member.exception.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberJoinSuccessResponse saveMember(MemberJoinRequest request) {
        Member member = createJoinMember(request);
        memberRepository.save(member);
        return MemberJoinSuccessResponse.from(member);
    }

    private Member createJoinMember(MemberJoinRequest request) {
        return Member.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
    }

    public void authMember(MemberAuthRequest request) {
        Member member = checkMember(request.email(), request.name());
        mailService.sendMail(member.getEmail());
    }

    @Transactional
    public void changeMemberPassword(ChangePasswordRequest request) {
        Member member = checkMember(request.email(), request.name());
        member.updatePassword(request.password());
    }

    private Member checkMember(String email, String name) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        if (!member.isMyName(name)) {
            throw new UnauthorizedException("멤버가 인증되지 않음");
        }
        return member;
    }
}
