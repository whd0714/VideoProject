package videoproject.video.videos;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import videoproject.video.creator.Creator;
import videoproject.video.creator.QCreator;
import videoproject.video.member.QMember;
import videoproject.video.subscribe.QSubScribe;

import javax.persistence.EntityManager;
import java.util.List;

import static videoproject.video.creator.QCreator.creator;
import static videoproject.video.member.QMember.member;
import static videoproject.video.subscribe.QSubScribe.subScribe;
import static videoproject.video.videos.QVideo.video;

public class VideoRepositoryImpl implements VideoCustomRepository{

    private final JPAQueryFactory queryFactory;

    public VideoRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Video> findSideVideos(Long videoId) {

        //Creator creatorByVideoId = findCreatorByVideoId(videoId);

        List<Video> fetch = queryFactory
                .select(video)
                .from(creator)
                .join(creator.videos, video)
                //.where(creator.id.eq(creatorByVideoId.getId()))
                .where(creatorVideosEq(videoId))
                .fetch();

        System.out.println();

        return fetch;
    }

    public Creator findCreatorByVideoId(Long videoId) {
        Creator creator = queryFactory
                .selectFrom(QCreator.creator)
                .where(QCreator.creator.videos.any().id.eq(videoId))
                .fetchOne();

        return creator;
    }

    @Override
    public List<Video> findSubscriptionVideos(Long memberId) {
        /*List<Video> fetch = queryFactory
                .selectFrom(video)
                .join(video.member, member).fetchJoin()
                .where(member.id.eq(memberId))
                .fetch();
        return fetch;*/
        return null;
    }

    private BooleanExpression creatorVideosEq(Long videoId) {
        return videoId != null ? creator.videos.any().id.eq(videoId) : null;
    }

  /*  private BooleanExpression videoMember(Long videoId) {
        return videoId != null ? member.videos.any().id.eq(videoId) : null;
    }*/
}
