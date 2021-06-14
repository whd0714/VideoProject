package videoproject.video.subscribe;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoproject.video.member.Member;
import videoproject.video.member.MemberRepository;
import videoproject.video.subscribe.dto.SubscribedDto;
import videoproject.video.subscribeMember.SubScribeMemberRepository;
import videoproject.video.subscribeMember.SubscribeMember;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final MemberRepository memberRepository;
    private final SubScribeMemberRepository subScribeMemberRepository;


    public void unsubscribe(SubscribedDto subscribedDto) {
        final Member[] creator = new Member[1];
        final Member[] subscriber = new Member[1];

        Optional<Member> creatorById = memberRepository.findById(subscribedDto.getCreatorId());
        creatorById.ifPresent(m->{
            creator[0] = m;
        });

        Optional<Member> subscribeById = memberRepository.findById(subscribedDto.getSubscriberId());
        subscribeById.ifPresent(m->{
            subscriber[0] = m;
        });

        SubScribe subscribe = subscribeRepository.findSubscribed(subscribedDto.getCreatorId(), subscribedDto.getSubscriberId());

        SubscribeMember subscribeMember = subScribeMemberRepository.findSubMemberBySubIdAndMemberId(subscribe.getId(), subscribedDto.getSubscriberId());
        subScribeMemberRepository.delete(subscribeMember);

    }

    public void subscribe(SubscribedDto subscribedDto) {
        final Member[] creator = new Member[1];
        final Member[] subscriber = new Member[1];

        Optional<Member> creatorById = memberRepository.findById(subscribedDto.getCreatorId());
        creatorById.ifPresent(m->{
            creator[0] = m;
        });

        Optional<Member> subscribeById = memberRepository.findById(subscribedDto.getSubscriberId());
        subscribeById.ifPresent(m->{
            subscriber[0] = m;
        });

        SubScribe subScribe = new SubScribe(creator[0]);
        SubscribeMember subscribeMember = new SubscribeMember(subscriber[0], subScribe);

        subscribeRepository.save(subScribe);
        subScribeMemberRepository.save(subscribeMember);

    }
}
