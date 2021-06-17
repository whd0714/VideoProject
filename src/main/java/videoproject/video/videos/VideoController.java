package videoproject.video.videos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import videoproject.video.member.CurrentUser;
import videoproject.video.member.Member;
import videoproject.video.member.MemberRepository;
import videoproject.video.subscribe.SubScribe;
import videoproject.video.subscribe.SubscribeRepository;
import videoproject.video.videos.dto.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;
    private final VideoRepository videoRepository;
    private final MemberRepository memberRepository;
    private final SubscribeRepository subscribeRepository;


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

/*    @PostMapping("/api/video/upload")
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
    }*/

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
    public Result getVideo(@RequestBody VideoIdDto videoIdDto) {
        Map map = new HashMap<String, Object>();

        Video video = videoRepository.findVideoDetailById(videoIdDto.getVideoId());

        videoService.viewUpdate(videoIdDto.getVideoId());

        DetailVideo detailVideo = new DetailVideo(video);

        return new Result(detailVideo);
    }

    @PostMapping("/api/video/sideVideo")
    public Result getSideVideos(@RequestBody VideoIdDto videoIdDto) {

        List<Video> sideVideos = videoRepository.findSideVideos(videoIdDto.getVideoId());

        List<DetailVideo> collect = sideVideos.stream().map(v -> new DetailVideo(v)).collect(Collectors.toList());

        return new Result(collect);
    }

    @PostMapping("/api/subscription/videos")
    public Result getSubscriptionVideos(@RequestBody MemberIdDto memberIdDto) {
        List<SubScribe> subscribes = subscribeRepository.findSubscribeByMemberId(memberIdDto.getMemberId());
        List<SubscriptionVideos> collect = subscribes.stream().map(s -> new SubscriptionVideos(s)).collect(Collectors.toList());

        Result result = new Result(collect);
        if(collect == null) {
            result.setSubscription(false);
        } else {
            result.setSubscription(true);
        }

        return result;
    }

    @Data
    static class SubscriptionVideos {
        private Long subScribeId;
        private Long creatorId;
        private String creatorName;
        private List<VideosDto> videosDtos;

        public SubscriptionVideos(SubScribe subScribe) {
            this.subScribeId = subScribe.getId();
            this.creatorId = subScribe.getCreator().getId();
            this.creatorName = subScribe.getCreator().getMember().getName();
            this.videosDtos = subScribe.getCreator().getVideos().stream().map(v->new VideosDto(v)).collect(Collectors.toList());
        }
    }

    @Data
    static class VideosDto{
        private Long videoId;
        private String title;
        private String description;
        private String filepath;
        private String thumbnailPath;
        private LocalDateTime uploadDate;
        private String duration;
        private String access;
        private String category;
        private int views;

        public VideosDto(Video video) {
            this.videoId = video.getId();
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
    static class DetailVideo {
        private Long creatorId;
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
            this.creatorId = video.getCreator().getId();
            this.name = video.getCreator().getMember().getName();
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
        private T result2;
        private boolean success;
        private boolean isSubscription;

        public Result(T data) {
            this.result2 = data;
            this.success = true;
        }
    }
}



