package videoproject.video.subscribe;


import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<SubScribe, Long>, SubscribeCustomRepository {
}
