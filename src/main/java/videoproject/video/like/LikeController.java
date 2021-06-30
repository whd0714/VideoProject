package videoproject.video.like;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import videoproject.video.like.dto.NewLikeDto;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeRepository likeRepository;
    private final LikeService likeService;

    @PostMapping("/api/like/new")
    public Map newLike(@RequestBody NewLikeDto newLikeDto) {
        Map map = new HashMap<String, Object>();

        System.out.println("!!!!!!!!!!!!!" + newLikeDto);


        return map;
    }
}
