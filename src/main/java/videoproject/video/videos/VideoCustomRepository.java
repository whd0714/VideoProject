package videoproject.video.videos;

import java.util.List;

public interface VideoCustomRepository {

    List<Video> findSideVideos(Long videoId);

    List<Video> findSubscriptionVideos(Long memberId);
}
