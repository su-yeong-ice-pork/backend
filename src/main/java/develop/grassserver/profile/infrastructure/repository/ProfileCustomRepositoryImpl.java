package develop.grassserver.profile.infrastructure.repository;

import com.querydsl.jpa.JPAExpressions;
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
    public long updateFreezeCount(Long memberId) {
        return jpaQueryFactory
                .update(profile)
                .set(profile.freeze.freezeCount, profile.freeze.freezeCount.add(1))
                .where(profile.id.eq(
                                JPAExpressions.select(member.profile.id)
                                        .from(member)
                                        .where(member.id.eq(memberId))
                        )
                )
                .execute();
    }
}
