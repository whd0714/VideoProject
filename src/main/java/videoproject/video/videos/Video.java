package videoproject.video.videos;

import videoproject.video.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Video {

    @Id @GeneratedValue
    @Column(name = "video_id")
    private Long id;

    private String title;
    private String description;
    private String filepath;
    private String thumbnailPath;
    private LocalDateTime uploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
