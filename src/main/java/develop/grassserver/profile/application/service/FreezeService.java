package develop.grassserver.profile.application.service;

import develop.grassserver.grass.domain.entity.GrassScoreAggregate;
import develop.grassserver.grass.infrastructure.repositiory.GrassScoreAggregateRepository;
import develop.grassserver.member.domain.entity.Member;
import develop.grassserver.member.infrastructure.repository.MemberRepository;
import develop.grassserver.profile.application.exception.NotEnoughGrassScoreException;
import develop.grassserver.profile.infrastructure.repository.ProfileRepository;
import develop.grassserver.profile.presentation.dto.FreezeCountResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FreezeService {

    private static final int FREEZE_PRICE = 50;

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final GrassScoreAggregateRepository grassScoreAggregateRepository;

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
    public void exchangeFreeze(Member member) {
        GrassScoreAggregate grassScoreAggregate = grassScoreAggregateRepository.findByMember(member)
                .orElseThrow(NotEnoughGrassScoreException::new);

        validateGrassScore(grassScoreAggregate);

        process(grassScoreAggregate, member);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void process(GrassScoreAggregate grassScoreAggregate, Member findMember) {
        grassScoreAggregate.subtractScore(FREEZE_PRICE);
        profileRepository.updateFreezeCount(findMember.getId());
    }

    private void validateGrassScore(GrassScoreAggregate grassScoreAggregate) {
        if (grassScoreAggregate.getGrassScore() <= FREEZE_PRICE) {
            throw new NotEnoughGrassScoreException();
        }
    }

    @Transactional
    public void useFreeze(Member member) {
        Member findMember = memberRepository.findByIdWithProfile(member.getId())
                .orElseThrow(EntityNotFoundException::new);

        findMember.getProfile().getFreeze().decrease();
    }
}
