package develop.grassserver.profile.application.service;

import develop.grassserver.grass.application.service.GrassScoreAggregateService;
import develop.grassserver.grass.application.service.MemberGrassService;
import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.grass.infrastructure.repositiory.GrassScoreAggregateRepository;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.member.infrastructure.repository.MemberRepository;
import develop.grassserver.profile.infrastructure.repository.ProfileRepository;
import develop.grassserver.profile.presentation.dto.FreezeCountResponse;
import develop.grassserver.profile.presentation.dto.FreezeExchangeQuantityRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FreezeService {

    private static final int FREEZE_PRICE = 50;

    private final MemberGrassService memberGrassService;
    private final GrassScoreAggregateService grassScoreAggregateService;

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public FreezeCountResponse getFreezeCount(Member member) {
        Member findMember = memberRepository.findByIdWithProfile(member.getId())
                .orElseThrow(EntityNotFoundException::new);
        return new FreezeCountResponse(getProfileFreezeCount(findMember));
    }

    private int getProfileFreezeCount(Member member) {
        return member.getProfile()
                .getFreeze()
                .getFreezeCount();
    }

    @Transactional
    public void exchangeFreeze(Member member, FreezeExchangeQuantityRequest request) {
        int exchangeQuantity = request.quantity();
        grassScoreAggregateService.subtractGrassScore(FREEZE_PRICE * exchangeQuantity, member);
        profileRepository.updateFreezeCount(member.getId(), exchangeQuantity);
    }

    @Transactional
    public void useFreeze(Member member) {
        Member findMember = memberRepository.findByIdWithProfile(member.getId())
                .orElseThrow(EntityNotFoundException::new);

        findMember.getProfile().getFreeze().decrease();
        memberGrassService.createAttendance(member);
    }
}
