package videoproject.video.subscribe;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import videoproject.video.member.QMember;

import javax.persistence.EntityManager;

import java.util.List;

import static videoproject.video.member.QMember.member;
import static videoproject.video.subscribe.QSubScribe.*;

public class SubscribeRepositoryImpl implements SubscribeCustomRepository{

    private final JPAQueryFactory queryFactory;

    public SubscribeRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Long findSubscribeNumber(Long createId) {
        long count = queryFactory
                .selectFrom(subScribe)
                .join(subScribe.creator, member)
                .where(member.id.eq(createId))
                .fetchCount();
        return count;
    }

    @Override
    public SubScribe findSubscribed(Long creatorId, Long subscriberId) {
        SubScribe subScribe = queryFactory
                .selectFrom(QSubScribe.subScribe)
                .join(QSubScribe.subScribe.creator, member)
                .where(creatorEq(creatorId), subscriberEq(subscriberId))
                .fetchOne();
        return subScribe;
    }

    private BooleanExpression subscriberEq(Long subscriberId) {
        return subscriberId!=null ? subScribe.subscribeMembers.any().member.id.eq(subscriberId) : null;
    }

    private BooleanExpression creatorEq(Long creatorId) {
        return creatorId != null ? subScribe.creator.id.eq(creatorId) : null;
    }


}
