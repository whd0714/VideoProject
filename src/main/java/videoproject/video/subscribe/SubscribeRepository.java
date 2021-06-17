package videoproject.video.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<SubScribe, Long>, SubscribeCustomRepository {

    @Query("select s from SubScribe s join s.subscriber m where m.id = :memberId")
    List<SubScribe> findSubscribeByMemberId(Long memberId);
}
