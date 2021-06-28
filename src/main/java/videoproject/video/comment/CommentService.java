package videoproject.video.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import videoproject.video.comment.dto.NestCommentDto;
import videoproject.video.comment.dto.RootCommentDto;
import videoproject.video.creator.Creator;
import videoproject.video.member.Member;
import videoproject.video.member.MemberRepository;
import videoproject.video.videos.Video;
import videoproject.video.videos.VideoRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final VideoRepository videoRepository;

    public Long rootComment(RootCommentDto rootCommentDto) {

        final Member[] members = new Member[1];
        final Video[] videos = new Video[1];

        Optional<Member> member = memberRepository.findById(rootCommentDto.getMemberId());
        member.ifPresent(m->{
            members[0] = m;
        });

        Optional<Video> video = videoRepository.findById(rootCommentDto.getVideoId());
        video.ifPresent(v->{
            videos[0] = v;
        });

        Comment comment = new Comment(members[0], videos[0], rootCommentDto.getComment());
        commentRepository.save(comment);

        return comment.getId();
    }

    public Long nestComment(NestCommentDto nestCommentDto) {

        final Member[] members = new Member[1];
        final Video[] videos = new Video[1];
        final Comment[] parents = new Comment[1];

        Optional<Member> member = memberRepository.findById(nestCommentDto.getMemberId());
        member.ifPresent(m->{
            members[0] = m;
        });

        Optional<Video> video = videoRepository.findById(nestCommentDto.getVideoId());
        video.ifPresent(v->{
            videos[0] = v;
        });

        Optional<Comment> comment = commentRepository.findById(nestCommentDto.getCommentId());
        comment.ifPresent(c->{
            parents[0] = c;
        });

        Comment newComment = new Comment(members[0], videos[0], nestCommentDto.getContent(), parents[0]);

        commentRepository.save(newComment);

        return newComment.getId();
    }
}
