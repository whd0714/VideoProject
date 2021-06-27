package videoproject.video.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoproject.video.member.Member;
import videoproject.video.videos.Video;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @OneToMany(mappedBy = "parent")
    private List<Comment> childComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void changeComment(Comment parent) {
        parent.getChildComments().add(this);
        this.parent = parent;
    }

    public Comment(Member member, Video video, String content) {
        this.member = member;
        member.getComments().add(this);
        this.video = video;
        video.getComments().add(this);
        this.content = content;
    }

    public Comment(Member member, Video video, String content, Comment parent) {
        this.member = member;
        member.getComments().add(this);
        this.video = video;
        video.getComments().add(this);
        this.content = content;
        this.content = content;
        changeComment(parent);
    }
}
