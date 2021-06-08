package videoproject.video.videos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoproject.video.member.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id @GeneratedValue
    @Column(name = "video_id")
    private Long id;

    private String title;
    private String description;
    private String filepath;
    private String thumbnailPath;
    private LocalDateTime uploadDate;
    private String duration;
    private String access;
    private String category;
    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Video(String title, String duration, String description, String access, String category,
                 String filepath, String thumbnailPath, Member member) {
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.access = access;
        this.category = category;
        this.filepath = filepath;
        this.thumbnailPath = thumbnailPath;
        this.uploadDate = LocalDateTime.now();
        this.views = 0;
        changeMember(member);
    }

    private void changeMember(Member member) {
        member.getVideos().add(this);
        this.member = member;
    }
}
