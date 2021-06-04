package videoproject.video.videos.dto;

import lombok.Data;

@Data
public class VideoUploadDto {

    private String title;
    private String duration;
    private String description;
    private String access;
    private String category;
    private String filepath;
    private String thumbnailPath;

}
