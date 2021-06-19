package videoproject.video.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import videoproject.video.comment.dto.RootCommentDto;

import javax.xml.transform.Result;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment/new/root")
    public Result newRootComment(@RequestBody RootCommentDto rootCommentDto) {

        commentService.rootComment(rootCommentDto);

        return null;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
        private boolean success;
    }
}
