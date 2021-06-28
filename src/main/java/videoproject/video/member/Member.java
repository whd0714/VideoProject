package videoproject.video.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoproject.video.comment.Comment;
import videoproject.video.creator.Creator;
import videoproject.video.subscribe.SubScribe;

import videoproject.video.videos.Video;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private LocalDateTime joinAt;
    private boolean isAdmin;

    @OneToMany(mappedBy = "subscriber", fetch = FetchType.LAZY)
    private List<SubScribe> subScribes = new ArrayList<>();

    @OneToOne(mappedBy = "member",fetch = FetchType.LAZY)
    private Creator creator;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", joinAt=" + joinAt +
                ", isAdmin=" + isAdmin +
                '}';
    }

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.joinAt = LocalDateTime.now();
    }

   /* public void changeSubscribe(SubScribe subScribe) {
        this.subScribe = subScribe;
    }*/


    public void creator(Creator creator) {
        this.creator = creator;
    }
}
