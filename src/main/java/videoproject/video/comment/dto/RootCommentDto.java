package videoproject.video.comment.dto;

import lombok.Data;

@Data
public class RootCommentDto {

    private Long videoId;
    private String comment;
    private Long memberId;
}
