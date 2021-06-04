package videoproject.video.videos;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import videoproject.video.member.Member;
import videoproject.video.member.MemberRepository;
import videoproject.video.videos.dto.ServerUploadVideoDto;

import javax.transaction.TransactionScoped;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@TransactionScoped
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final MemberRepository memberRepository;

    private static final String VIDEO_PATH = "F:\\VideoProject\\src\\main\\resources\\static\\upload\\video\\";

    public Map serverUpload(ServerUploadVideoDto serverUploadVideoDto) {
        String filename = serverUploadVideoDto.getFile().getOriginalFilename();
        String saveFile = VIDEO_PATH + filename;

        try {
            serverUploadVideoDto.getFile().transferTo(new File(saveFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map map = new HashMap<String, Object>();
        map.put("filename",filename);
        map.put("filepath", saveFile);
        map.put("success", true);
        return map;
    }
}
