package videoproject.video.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import videoproject.video.comment.dto.NestCommentDto;
import videoproject.video.comment.dto.RootCommentDto;
import videoproject.video.comment.dto.VideoIdDto;
import videoproject.video.member.Member;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/api/comment/new/root")
    public Map newRootComment(@RequestBody RootCommentDto rootCommentDto) {

        Map map = new HashMap<String, Object>();
        Long commentId = commentService.rootComment(rootCommentDto);

        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(c-> {
            CommentByVideoDto commentByVideoDto = new CommentByVideoDto(c);
            map.put("result", commentByVideoDto);
            map.put("success", true);
        });

        return map;
    }

    @PostMapping("/api/comment/getComment")
    public Result getComment(@RequestBody VideoIdDto videoIdDto) {

        List<Comment> comments = commentRepository.findAllCommentByVideoId(videoIdDto.getVideoId());

        List<CommentByVideoDto> collect = comments.stream().map(c -> new CommentByVideoDto(c)).collect(Collectors.toList());


        Result result = new Result(collect);

        return result;
    }

    @PostMapping("/api/comment/replay")
    public Map addNestedComment(@RequestBody NestCommentDto nestCommentDto) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!" + nestCommentDto);
        Map map = new HashMap<String, Object>();

        Long commentId = commentService.nestComment(nestCommentDto);
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(c->{
            CommentByVideoDto commentByVideoDto = new CommentByVideoDto(c);
            map.put("result", commentByVideoDto);
            map.put("success",true);
        });

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!" + map);

        return map;
    }

    @Data
    static class CommentByVideoDto {
        private Long commentId;
        private String content;
        private Long memberId;
        private String memberName;
        private String memberEmail;
        private Long parentId;

        public CommentByVideoDto(Comment comment) {
            this.commentId = comment.getId();
            this.content = comment.getContent();
            this.memberId = comment.getMember().getId();
            this.memberName = comment.getMember().getName();
            this.memberEmail = comment.getMember().getEmail();
            if(comment.getParent()!=null) {
                this.parentId = comment.getParent().getId();
            }
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T result;
        private boolean success;
        private String aaa;

        public Result(T result) {
            this.result = result;
            success = true;
            aaa="qqqqqqqqqqqqqqqqqqqqqqqqqqqqq";
        }
    }
}
