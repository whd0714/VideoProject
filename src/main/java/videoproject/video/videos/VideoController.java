package videoproject.video.videos;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import videoproject.video.member.CurrentUser;
import videoproject.video.member.Member;
import videoproject.video.videos.dto.ServerUploadVideoDto;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/api/video/server/upload")
    public Map videoServerUpload(@Valid @ModelAttribute ServerUploadVideoDto serverUploadVideoDto, Errors errors) {
        System.out.println(serverUploadVideoDto);
        Map map = new HashMap<String, Object>();
        if (errors.hasErrors()) {
            map.put("success", false);
            return map;
        }
        map = videoService.serverUpload(serverUploadVideoDto);

        return map;
    }

}



