package videoproject.video.videos;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import videoproject.video.member.QMember;

import javax.persistence.EntityManager;
import java.util.List;

import static videoproject.video.member.QMember.member;
import static videoproject.video.videos.QVideo.video;

public class VideoRepositoryImpl implements VideoCustomRepository{

    private final JPAQueryFactory queryFactory;

    public VideoRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Video> findSideVideos(Long videoId) {
        List<Video> fetch = queryFactory
                .selectFrom(video)
                .join(video.member, member).fetchJoin()
                .where(videoMember(videoId))
                .fetch();
        return fetch;
    }

    private BooleanExpression videoMember(Long videoId) {
        return videoId != null ? member.videos.any().id.eq(videoId) : null;
    }
}
