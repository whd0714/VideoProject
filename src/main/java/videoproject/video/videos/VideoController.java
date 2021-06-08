package videoproject.video.videos;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import videoproject.video.member.CurrentUser;
import videoproject.video.member.Member;
import videoproject.video.videos.dto.ServerUploadVideoDto;
import videoproject.video.videos.dto.VideoThumbnailDto;
import videoproject.video.videos.dto.VideoUploadDto;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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

    @PostMapping("/api/video/thumbnail")
    public Map videoThumbnail(@Valid @RequestBody VideoThumbnailDto videoThumbnailDto, Errors errors, HttpServletResponse response) throws IOException {
        System.out.println(videoThumbnailDto);

        Map map = new HashMap<String, Object>();
        if (errors.hasErrors()) {
            map.put("success", false);
            return map;
        }

        map = videoService.videoThumbnail(videoThumbnailDto);
        response.setCharacterEncoding("UTF-8");
        return map;
    }

    @PostMapping("/api/video/upload")
    public Map videoUpload(@CurrentUser Member member, @Valid @RequestBody VideoUploadDto videoUploadDto, Errors errors) {
        Map map = new HashMap<String, Object>();
        if(errors.hasErrors()) {
            map.put("success", false);
            return map;
        }
        if(member != null) {
            videoService.videoUpload(videoUploadDto, member.getId());
        }

        map.put("success", true);
        return map;
    }

}



