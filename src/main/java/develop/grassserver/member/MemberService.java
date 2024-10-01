package develop.grassserver.member;

import develop.grassserver.mail.MailService;
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

    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member id : " + id));
    }

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
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(EntityNotFoundException::new);
        if (!member.isMyName(request.name())) {
            throw new UnauthorizedException("멤버가 인증되지 않음");
        }
        mailService.sendMail(member.getEmail());
    }
}
