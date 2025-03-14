package develop.grassserver.profile.infrastructure.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import develop.grassserver.member.domain.entity.QMember;
import develop.grassserver.profile.domain.entity.QProfile;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileCustomRepositoryImpl implements ProfileCustomRepository {

    private final QMember member;
    private final QProfile profile;
    private final JPAQueryFactory jpaQueryFactory;

    public ProfileCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.member = QMember.member;
        this.profile = QProfile.profile;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public long updateFreezeCount(Long memberId, int quantity) {
        return jpaQueryFactory
                .update(profile)
                .set(profile.freeze.freezeCount, profile.freeze.freezeCount.add(quantity))
                .where(isEqualProfile(memberId))
                .execute();
    }

    private BooleanExpression isEqualProfile(Long memberId) {
        return profile.id.eq(selectEqualMemberId(memberId));
    }

    private JPQLQuery<Long> selectEqualMemberId(Long memberId) {
        return JPAExpressions.select(member.profile.id)
                .from(member)
                .where(isEqualMemberId(memberId));
    }

    private BooleanExpression isEqualMemberId(Long memberId) {
        return member.id.eq(memberId);
    }
}
