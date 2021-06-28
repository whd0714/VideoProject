package videoproject.video.creator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoproject.video.member.Member;
import videoproject.video.videos.Video;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Creator {

    @Id @GeneratedValue
    @Column(name = "creator_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_Id")
    private Member member;

    @OneToMany(mappedBy = "creator")
    private List<Video> videos = new ArrayList<>();

    @Override
    public String toString() {
        return "Creator{" +
                "id=" + id +
                '}';
    }

    public Creator(Member member) {
        this.member = member;
        //member.creator(this);
    }

}
