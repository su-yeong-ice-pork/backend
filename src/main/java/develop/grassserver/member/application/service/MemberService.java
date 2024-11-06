package develop.grassserver.member.application.service;

import develop.grassserver.badge.application.service.BadgeService;
import develop.grassserver.member.application.exception.UnauthorizedException;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.member.infrastructure.repository.MemberRepository;
import develop.grassserver.member.presentation.dto.ChangePasswordRequest;
import develop.grassserver.member.presentation.dto.DeleteMemberRequest;
import develop.grassserver.member.presentation.dto.MemberJoinRequest;
import develop.grassserver.member.presentation.dto.MemberProfileResponse;
import develop.grassserver.profile.domain.entity.Profile;
import develop.grassserver.profile.infrastructure.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final BadgeService badgeService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public MemberProfileResponse findMemberProfile(Member member) {
        Member persistMember = memberRepository.findByIdWithProfile(member.getId())
                .orElseThrow(EntityNotFoundException::new);
        return MemberProfileResponse.from(persistMember);
    }

    @Transactional
    public void saveMember(MemberJoinRequest request) {
        Profile profile = request.toProfileEntity();
        profileRepository.save(profile);

        Member member = request.toMemberEntity(request, passwordEncoder.encode(request.password()), profile);
        memberRepository.save(member);

        badgeService.saveBetaMemberBadge(member);
    }

    @Transactional
    public void changeMemberPassword(ChangePasswordRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(EntityNotFoundException::new);

        if (!member.isMyName(request.name())) {
            throw new UnauthorizedException();
        }

        String newPassword = passwordEncoder.encode(request.password());
        member.updatePassword(newPassword);
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void deleteMember(DeleteMemberRequest request, Member member) {
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(EntityNotFoundException::new);

        String memberPassword = findMember.getPassword();
        if (!passwordEncoder.matches(request.password(), memberPassword)) {
            throw new UnauthorizedException();
        }

        memberRepository.delete(findMember);
    }
}
