package develop.grassserver.member;

import develop.grassserver.mail.MailService;
import develop.grassserver.member.badge.BadgeService;
import develop.grassserver.member.dto.ChangePasswordRequest;
import develop.grassserver.member.dto.MemberAuthRequest;
import develop.grassserver.member.dto.MemberJoinRequest;
import develop.grassserver.member.dto.MemberProfileResponse;
import develop.grassserver.member.exception.UnauthorizedException;
import develop.grassserver.member.profile.Profile;
import develop.grassserver.member.profile.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private static final String DEFAULT_PROFILE_IMAGE =
            "https://grass-bucket.s3.us-east-2.amazonaws.com/%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%91%E1%85%B5%E1%86%AF%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B5.png";
    private static final String DEFAULT_PROFILE_MESSAGE = "오늘 하루도 화이팅!";

    private final MailService mailService;
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
        Member member = createJoinMember(request);
        memberRepository.save(member);

        badgeService.saveBetaMemberBadge(member);
    }

    private Member createJoinMember(MemberJoinRequest request) {
        return Member.builder()
                .name(request.name())
                .email(request.email())
                .major(new Major(request.college(), request.department()))
                .profile(createMemberProfile())
                .password(passwordEncoder.encode(request.password()))
                .build();
    }

    private Profile createMemberProfile() {
        Profile profile = Profile.builder()
                .image(DEFAULT_PROFILE_IMAGE)
                .message(DEFAULT_PROFILE_MESSAGE)
                .freeze(new Freeze())
                .mainTitle("초심자")
                .mainBanner("https://grass-bucket.s3.us-east-2.amazonaws.com/bannerImage1.png")
                .build();
        return profileRepository.save(profile);
    }

    public void authMember(MemberAuthRequest request) {
        Member member = checkMember(request.email(), request.name());
        mailService.sendMail(member.getEmail());
    }

    @Transactional
    public void changeMemberPassword(ChangePasswordRequest request) {
        Member member = checkMember(request.email(), request.name());
        String newPassword = passwordEncoder.encode(request.password());
        member.updatePassword(newPassword);
    }

    private Member checkMember(String email, String name) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        if (!member.isMyName(name)) {
            throw new UnauthorizedException("멤버가 인증되지 않음");
        }
        return member;
    }
    
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
    }
}
