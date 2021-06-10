package videoproject.video.videos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("select v from Video v join fetch v.member m where v.id = :videoId")
    Video findVideoDetailById(Long videoId);
}
