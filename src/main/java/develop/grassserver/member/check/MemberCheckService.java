package develop.grassserver.member.check;

import develop.grassserver.member.MemberRepository;
import develop.grassserver.member.exception.DuplicateMemberException;
import develop.grassserver.member.exception.MemberNameFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCheckService {

    private final MemberRepository memberRepository;

    public void checkMemberName(String name) {
        if (memberRepository.existsByName(name)) {
            throw new DuplicateMemberException("멤버 이름이 중복됩니다.");
        }
        if (!MemberValidator.isCorrectNameFormat(name)) {
            throw new MemberNameFormatException("멤버 이름 형식 오류입니다.");
        }
    }
}
