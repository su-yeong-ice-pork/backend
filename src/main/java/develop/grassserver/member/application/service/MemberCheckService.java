package develop.grassserver.member.application.service;

import develop.grassserver.member.application.exception.DuplicateMemberException;
import develop.grassserver.member.application.exception.MemberEmailFormatException;
import develop.grassserver.member.application.exception.MemberNameFormatException;
import develop.grassserver.member.application.valid.MemberValidator;
import develop.grassserver.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCheckService {

    private final MemberRepository memberRepository;

    public void checkMemberName(String name) {
        if (memberRepository.existsByName(name))
            throw new DuplicateMemberException("멤버 이름이 중복됩니다.");
        if (!MemberValidator.isCorrectNameFormat(name)) {
            throw new MemberNameFormatException("멤버 이름 형식 오류입니다.");
        }
    }

    public void checkMemberEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateMemberException("멤버 이메일이 중복됩니다.");
        }
        if (!MemberValidator.isCorrectEmailFormat(email)) {
            throw new MemberEmailFormatException("멤버 이메일 형식 오류입니다.");
        }
    }
}
