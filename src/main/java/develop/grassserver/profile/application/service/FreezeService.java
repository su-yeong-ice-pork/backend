package develop.grassserver.profile.application.service;

import develop.grassserver.grass.application.service.GrassScoreAggregateService;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.member.infrastructure.repository.MemberRepository;
import develop.grassserver.profile.infrastructure.repository.ProfileRepository;
import develop.grassserver.profile.presentation.dto.FreezeCountResponse;
import develop.grassserver.profile.presentation.dto.FreezeExchangeQuantityRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FreezeService {

    private static final int FREEZE_PRICE = 50;

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

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void exchangeFreeze(Member member, FreezeExchangeQuantityRequest request) {
        updateGrassScoreAndFreezeCount(member, request.quantity());
    }

    private void updateGrassScoreAndFreezeCount(Member findMember, int quantity) {
        grassScoreAggregateService.subtractGrassScore(FREEZE_PRICE * quantity, findMember);
        profileRepository.updateFreezeCount(findMember.getId(), quantity);
    }
}
