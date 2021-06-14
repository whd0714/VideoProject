package videoproject.video.subscribe;

import java.util.List;

public interface SubscribeCustomRepository {


    Long findSubscribeNumber(Long createId);

    SubScribe findSubscribed(Long creatorId, Long subscriberId);
}
