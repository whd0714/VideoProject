package videoproject.video.videos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import videoproject.video.member.CurrentUser;
import videoproject.video.member.Member;
import videoproject.video.videos.dto.ServerUploadVideoDto;
import videoproject.video.videos.dto.VideoDetailDto;
import videoproject.video.videos.dto.VideoThumbnailDto;
import videoproject.video.videos.dto.VideoUploadDto;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final VideoRepository videoRepository;

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

    @PostMapping("/api/video/getVideo")
    public Result getVideo(@RequestBody VideoDetailDto videoDetailDto) {
        Map map = new HashMap<String, Object>();

        Video video = videoRepository.findVideoDetailById(videoDetailDto.getVideoId());

        DetailVideo detailVideo = new DetailVideo(video);

        return new Result(detailVideo);
    }

    @Data
    static class DetailVideo {
        private String name;
        private String title;
        private String description;
        private String filepath;
        private String thumbnailPath;
        private LocalDateTime uploadDate;
        private String duration;
        private String access;
        private String category;
        private int views;

        public DetailVideo(Video video) {
            this.name = video.getMember().getName();
            this.title = video.getTitle();
            this.description = video.getDescription();
            this.filepath = video.getFilepath();
            this.thumbnailPath = video.getThumbnailPath();
            this.uploadDate = video.getUploadDate();
            this.duration = video.getDuration();
            this.access = video.getAccess();
            this.category = video.getCategory();
            this.views = video.getViews();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
        private boolean success;

        public Result(T data) {
            this.data = data;
            this.success = true;
        }
    }
}



