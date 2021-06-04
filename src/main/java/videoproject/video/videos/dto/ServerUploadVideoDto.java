package videoproject.video.videos.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ServerUploadVideoDto {

    private MultipartFile file;

}
