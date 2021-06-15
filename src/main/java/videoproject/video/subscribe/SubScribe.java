package videoproject.video.subscribe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoproject.video.member.Member;
import videoproject.video.subscribeMember.SubscribeMember;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member creator;

    @OneToMany(mappedBy = "subscribe", orphanRemoval = true)
    private List<SubscribeMember> subscribeMembers = new ArrayList<>();

    public SubScribe(Member creator) {
        this.creator = creator;
        //creator.changeSubscribe(this);
    }
}
