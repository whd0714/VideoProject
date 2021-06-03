package videoproject.video.member;

import org.springframework.data.jpa.repository.JpaRepository;
import videoproject.video.member.dto.MemberLoginDto;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    Member findByEmail(MemberLoginDto memberLoginDto);
}
