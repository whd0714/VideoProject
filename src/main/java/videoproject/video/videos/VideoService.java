package videoproject.video.videos;

import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.stereotype.Service;
import videoproject.video.creator.Creator;
import videoproject.video.creator.CreatorRepository;
import videoproject.video.member.Member;
import videoproject.video.member.MemberRepository;
import videoproject.video.videos.dto.ServerUploadVideoDto;
import videoproject.video.videos.dto.VideoThumbnailDto;
import videoproject.video.videos.dto.VideoUploadDto;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;

    private static final String VIDEO_PATH = "F:\\VideoProject\\src\\main\\resources\\static\\upload\\video\\";
    private static final String THUMBNAIL_SAVE_PATH = "F:\\VideoProject\\src\\main\\resources\\static\\upload\\thumbnail\\";


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

    public Map videoThumbnail(VideoThumbnailDto videoThumbnailDto) throws IOException {
        String filepath = videoThumbnailDto.getFilepath();
        String filename = videoThumbnailDto.getFilename();
        String ffmpegBasePath = "C:\\Users\\whd07\\ffmpeg\\bin\\";
        FFprobe ffprobe = new FFprobe(ffmpegBasePath+"ffprobe");

        FFmpegProbeResult probe = ffprobe.probe(filepath);
        FFmpegFormat format = probe.getFormat();
        double duration = format.duration;

        FFmpeg ffmpeg = new FFmpeg(ffmpegBasePath+"ffmpeg");

        FFmpegBuilder builder = new FFmpegBuilder()
                .overrideOutputFiles(true)					// output ????????? ????????? ????????? ??????(false??? ??????, output path??? ?????? ????????? ????????? ?????? ?????? ?????? - File 'C:/Users/Desktop/test.png' already exists. Exiting.)
                .setInput(filepath)     					// ????????? ????????? ????????? ????????? ?????? ????????? ?????? ??????
                .addExtraArgs("-ss", "00:00:05") 			// ???????????? ??????????????? ?????? ?????? - 00:00:01??? 1?????? ??????
                .addOutput(THUMBNAIL_SAVE_PATH + filename +".jpg") 		// ?????? ?????? ??????(????????? ??? ?????? ??? ?????? ?????? - [NULL @ 000002cc1f9fa500] Unable to find a suitable output format for 'C:/Users/Desktop/test')
                .setFrames(1)
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);		// FFmpeg ????????? ????????? ?????? FFmpegExecutor ?????? ??????
        executor.createJob(builder).run();

        String filePath = "upload/thumbnail/" + filename +".jpg";
        String videoPath = "upload/video/" + filename;

        Map map = new HashMap<String, Object>();
        map.put("filepath", filePath);
        map.put("videoPath", videoPath);
        map.put("duration", duration);
        map.put("success", true);

        return map;
    }


    public void videoUpload(VideoUploadDto videoUploadDto, Long id) {
        Optional<Member> member = memberRepository.findById(id);
        member.ifPresent(m -> {
            Creator creator = m.getCreator();
            if(creator==null){
                creator = new Creator(m);
            }

            Video video = new Video(videoUploadDto.getTitle(), videoUploadDto.getDuration(), videoUploadDto.getDescription(),
                    videoUploadDto.getAccess(), videoUploadDto.getCategory(), videoUploadDto.getFilepath(),
                    videoUploadDto.getThumbnailPath(), creator);
            creatorRepository.save(creator);
            videoRepository.save(video);
        });

    }

    public void viewUpdate(Long videoId) {
        Optional<Video> video = videoRepository.findById(videoId);
        video.ifPresent(v->{
            v.updateView();
        });
    }
}
