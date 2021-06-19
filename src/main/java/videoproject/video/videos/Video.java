package videoproject.video.videos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoproject.video.comment.Comment;
import videoproject.video.creator.Creator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @OneToMany(mappedBy = "video")
    private List<Comment> comments = new ArrayList<>();

    public Video(String title, String duration, String description, String access, String category,
                 String filepath, String thumbnailPath, Creator creator) {
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.access = access;
        this.category = category;
        this.filepath = filepath;
        this.thumbnailPath = thumbnailPath;
        this.uploadDate = LocalDateTime.now();
        this.views = 0;
        changeMember(creator);
    }

    public void changeMember(Creator creator) {
        creator.getVideos().add(this);
        this.creator = creator;
    }

    public void updateView() {
        this.views +=1;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", filepath='" + filepath + '\'' +
                ", thumbnailPath='" + thumbnailPath + '\'' +
                ", uploadDate=" + uploadDate +
                ", duration='" + duration + '\'' +
                ", access='" + access + '\'' +
                ", category='" + category + '\'' +
                ", views=" + views +
                '}';
    }
}
