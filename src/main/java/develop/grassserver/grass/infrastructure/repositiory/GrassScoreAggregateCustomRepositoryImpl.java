package develop.grassserver.grass.infrastructure.repositiory;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import develop.grassserver.grass.domain.entity.QGrassScoreAggregate;
import develop.grassserver.member.domain.entity.Member;

public class GrassScoreAggregateCustomRepositoryImpl implements GrassScoreAggregateCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QGrassScoreAggregate grassScoreAggregate;

    public GrassScoreAggregateCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.grassScoreAggregate = QGrassScoreAggregate.grassScoreAggregate;
    }

    @Override
    public long subtractScore(int score, Member member) {
        return jpaQueryFactory
                .update(grassScoreAggregate)
                .set(grassScoreAggregate.grassScore, grassScoreAggregate.grassScore.subtract(score))
                .where(isEqualMember(member)
                        .and(isEnoughScore(score))
                        .and(isEnoughAfterSubtractScore(score))
                )
                .execute();
    }

    private BooleanExpression isEqualMember(Member member) {
        return grassScoreAggregate.member.eq(member);
    }

    private BooleanExpression isEnoughScore(int score) {
        return grassScoreAggregate.grassScore.goe(score);
    }

    private BooleanExpression isEnoughAfterSubtractScore(int score) {
        return grassScoreAggregate.grassScore.subtract(score)
                .goe(0);
    }
}
