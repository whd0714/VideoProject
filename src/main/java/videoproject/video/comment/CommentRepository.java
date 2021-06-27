package videoproject.video.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.video v join fetch c.member m where v.id = :videoId")
    List<Comment> findAllCommentByVideoId(Long videoId);
}
