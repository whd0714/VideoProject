package videoproject.video.subscribeMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SubScribeMemberRepository extends JpaRepository<SubscribeMember, Long> {

    @Query("select sm from SubscribeMember sm join fetch sm.subscribe s join fetch sm.member m where s.id = :subscribeId and m.id = :subscriberId")
    SubscribeMember findSubMemberBySubIdAndMemberId(Long subscribeId, Long subscriberId);
}
