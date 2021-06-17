package videoproject.video.subscribe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoproject.video.creator.Creator;
import videoproject.video.member.Member;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubScribe
{

    @Id @GeneratedValue
    @Column(name = "subscribe_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member subscriber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;

    public SubScribe(Creator creator, Member subscriber) {
        this.subscriber = subscriber;
        this.creator = creator;
    }
}
