package develop.grassserver.member;

import jakarta.persistence.EntityNotFoundException;
import develop.grassserver.member.dto.MemberJoinRequest;
import develop.grassserver.member.dto.MemberJoinSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

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
                .password(request.password())
                .build();
    }
}
