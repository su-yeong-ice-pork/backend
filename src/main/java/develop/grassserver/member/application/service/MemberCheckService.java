package develop.grassserver.member.application.service;

import develop.grassserver.auth.application.service.MailService;
import develop.grassserver.auth.application.service.RedisService;
import develop.grassserver.member.application.exception.DuplicateMemberException;
import develop.grassserver.member.application.exception.MemberEmailFormatException;
import develop.grassserver.member.application.exception.MemberNameFormatException;
import develop.grassserver.member.application.exception.UnauthorizedException;
import develop.grassserver.member.application.valid.MemberValidator;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.member.infrastructure.repository.MemberRepository;
import develop.grassserver.member.presentation.dto.CheckAuthCodeRequest;
import develop.grassserver.member.presentation.dto.MemberAuthRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCheckService {

    private final MailService mailService;
    private final RedisService redisService;
    private final MemberRepository memberRepository;

    public void checkMemberName(String name) {
        if (memberRepository.existsByName(name)) {
            throw new DuplicateMemberException();
        }
        if (!MemberValidator.isCorrectNameFormat(name)) {
            throw new MemberNameFormatException();
        }
    }

    public void checkMemberEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateMemberException();
        }
        if (!MemberValidator.isCorrectEmailFormat(email)) {
            throw new MemberEmailFormatException();
        }

        mailService.sendMail(email);
    }

    public void checkAuthCode(CheckAuthCodeRequest request) {
        redisService.checkAuthCode(request);
    }

    public void checkSameMember(MemberAuthRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                        .orElseThrow(EntityNotFoundException::new);

        if (member.isMyName(request.name())) {
            throw new UnauthorizedException();
        }

        mailService.sendMail(member.getEmail());
    }
}
