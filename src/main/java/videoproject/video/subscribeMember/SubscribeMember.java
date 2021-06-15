package videoproject.video.subscribeMember;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoproject.video.member.Member;
import videoproject.video.subscribe.SubScribe;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeMember {

    @Id @GeneratedValue
    @Column(name = "subscribe_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subscribe_id")
    private SubScribe subscribe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public SubscribeMember(Member member, SubScribe subScribe) {
        this.member = member;
        member.getSubscribeMembers().add(this);

        this.subscribe = subScribe;

    }

    public void changeSubscribe(SubScribe subscribe) {
        this.subscribe = subscribe;
        subscribe.getSubscribeMembers().add(this);
    }
}
