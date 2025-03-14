package develop.grassserver.grass.infrastructure.repositiory;

import develop.grassserver.member.domain.entity.Member;

public interface GrassScoreAggregateCustomRepository {

    long subtractScore(int score, Member member);
}
