package videoproject.video.subscribe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoproject.video.creator.Creator;
import videoproject.video.creator.CreatorRepository;
import videoproject.video.member.Member;
import videoproject.video.member.MemberRepository;
import videoproject.video.subscribe.dto.SubscribedDto;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;

    public void unsubscribe(SubscribedDto subscribedDto) {
        final Creator[] creator = new Creator[1];
        final Member[] subscriber = new Member[1];

        Optional<Creator> creatorById = creatorRepository.findById(subscribedDto.getCreatorId());
        creatorById.ifPresent(c->{
            creator[0] = c;
        });

        Optional<Member> subscribeById = memberRepository.findById(subscribedDto.getSubscriberId());
        subscribeById.ifPresent(m->{
            subscriber[0] = m;
        });

        SubScribe subscribe = subscribeRepository.findSubscribed(subscribedDto.getCreatorId(), subscribedDto.getSubscriberId());
        subscribeRepository.delete(subscribe);

    }

    public void subscribe(SubscribedDto subscribedDto) {
        final Creator[] creator = new Creator[1];
        final Member[] subscriber = new Member[1];

        Optional<Creator> creatorById = creatorRepository.findById(subscribedDto.getCreatorId());
        creatorById.ifPresent(c->{
            creator[0] = c;
        });

        Optional<Member> subscribeById = memberRepository.findById(subscribedDto.getSubscriberId());
        subscribeById.ifPresent(m->{
            subscriber[0] = m;
        });

        SubScribe subScribe = new SubScribe(creator[0], subscriber[0]);

        subscribeRepository.save(subScribe);

    }
}