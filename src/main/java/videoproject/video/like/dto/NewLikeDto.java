package videoproject.video.like.dto;

import lombok.Data;

@Data
public class NewLikeDto {

    private Long commentId;

    private Long videoId;

    private Long memberId;
}
