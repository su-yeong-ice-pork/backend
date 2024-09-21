package develop.grassserver.member.check;

import develop.grassserver.member.MemberRepository;
import develop.grassserver.member.exception.DuplicateMemberException;
import develop.grassserver.member.exception.MemberIdFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCheckService {

    private final MemberRepository memberRepository;

    public void checkMemberId(String memberId) {
        if (memberRepository.existsByLoginId(memberId)) {
            throw new DuplicateMemberException("멤버 ID가 중복됩니다.");
        }
        if (!MemberValidator.isCorrectLoginIdFormat(memberId)) {
            throw new MemberIdFormatException("멤버 ID 형식 오류입니다.");
        }
    }
}
