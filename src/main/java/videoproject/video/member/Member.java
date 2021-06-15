package videoproject.video.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoproject.video.subscribe.SubScribe;
import videoproject.video.subscribeMember.SubscribeMember;
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

    @OneToMany(mappedBy = "member")
    private List<Video> videos = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<SubscribeMember> subscribeMembers = new ArrayList<>();

/*    @OneToOne(mappedBy = "creator", fetch = FetchType.LAZY)
    private SubScribe subScribe;*/

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.joinAt = LocalDateTime.now();
    }

   /* public void changeSubscribe(SubScribe subScribe) {
        this.subScribe = subScribe;
    }*/

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
}
