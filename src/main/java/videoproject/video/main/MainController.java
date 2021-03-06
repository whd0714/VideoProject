package videoproject.video.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import videoproject.video.member.Member;
import videoproject.video.videos.Video;
import videoproject.video.videos.VideoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final VideoRepository videoRepository;

    @GetMapping("/api/videos")
    public Result mainVideos() {
        List<Video> videos = videoRepository.findAll();

        List<RecommendVideosDto> collect = videos.stream().map(v -> new RecommendVideosDto(v)).collect(Collectors.toList());
        Result result = new Result(collect, true);

        return result;
    }

    @Data
    static class RecommendVideosDto {
        private Long id;
        private Long memberId;
        private String title;
        private String duration;
        private String filepath;
        private String thumbnailPath;
        private int views;
        private LocalDateTime uploadDate;
        private String name;
        private String description;

        public RecommendVideosDto(Video video) {
            this.id = video.getId();
            this.memberId = video.getCreator().getId();
            this.title = video.getTitle();
            this.duration = video.getDuration();
            this.filepath = video.getFilepath();
            this.thumbnailPath = video.getThumbnailPath();
            this.views = video.getViews();
            this.uploadDate = video.getUploadDate();
            this.name = video.getCreator().getMember().getName();
            this.description = video.getDescription();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T result;
        private boolean success;
    }

}
