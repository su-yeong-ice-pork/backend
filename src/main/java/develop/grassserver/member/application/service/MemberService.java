package develop.grassserver.member.application.service;

import develop.grassserver.auth.application.service.JwtService;
import develop.grassserver.badge.application.service.BadgeService;
import develop.grassserver.friend.infrastructure.repository.FriendRepository;
import develop.grassserver.member.application.exception.UnauthorizedException;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.member.infrastructure.repository.MemberRepository;
import develop.grassserver.member.presentation.dto.ChangePasswordRequest;
import develop.grassserver.member.presentation.dto.DeleteMemberRequest;
import develop.grassserver.member.presentation.dto.FindMemberResponse;
import develop.grassserver.member.presentation.dto.FindOtherMemberResponse;
import develop.grassserver.member.presentation.dto.MemberJoinRequest;
import develop.grassserver.member.presentation.dto.MemberProfileResponse;
import develop.grassserver.profile.domain.entity.Profile;
import develop.grassserver.profile.infrastructure.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final JwtService jwtService;
    private final BadgeService badgeService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    /*
        디자인 패턴으로 순환 참조를 풀어내야 함
        현재 FriendService 에서 MemberService 의존
        상대 멤버 프로필 조회 시 친구 관계를 찾아내야 하는데,
        이때 MemberService 에서도 FriendService 를 의존 하려고 함
        순환 참조 발생 !!
        임시로 FriendRepository 를 직접 주입 받음
     */
    private final FriendRepository friendRepository;

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

        findMember.deleteEmailAndName();

        jwtService.deleteRefreshToken(findMember.getEmail());
    }

    public FindMemberResponse findMemberByNameOrEmail(String keyword) {
        boolean isEmail = keyword.contains("@pusan.ac.kr");
        Optional<Member> optionalMember = getOptionalMemberByNameOrEmail(isEmail, keyword);
        return optionalMember.map(FindMemberResponse::from)
                .orElseThrow(EntityNotFoundException::new);
    }

    private Optional<Member> getOptionalMemberByNameOrEmail(boolean isEmail, String keyword) {
        if (isEmail) {
            return memberRepository.findByEmailWithProfile(keyword);
        }
        return memberRepository.findByNameWithProfile(keyword);
    }

    public FindOtherMemberResponse findOtherMember(Long id, Member member) {
        Member me = memberRepository.findById(member.getId())
                .orElseThrow(EntityNotFoundException::new);
        Member other = memberRepository.findByIdWithProfile(id)
                .orElseThrow(EntityNotFoundException::new);

        boolean isMyFriend = friendRepository.findFriend(me.getId(), other.getId()).isPresent();
        return FindOtherMemberResponse.from(isMyFriend, other);
    }

    public List<Member> findAllMembersByIds(List<Long> ids) {
        return memberRepository.findAllByIdsWithProfile(ids);
    }
}
