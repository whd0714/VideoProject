package videoproject.video.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoproject.video.member.dto.MemberLoginDto;
import videoproject.video.member.dto.MemberRegisterDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(MemberRegisterDto memberRegisterDto) {
        String password = passwordEncoder.encode(memberRegisterDto.getPassword());
        Member member = new Member(memberRegisterDto.getName(), memberRegisterDto.getEmail(), password);

        memberRepository.save(member);
    }

    public void login(Member member) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserMember(member),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public Map loginProcess(MemberLoginDto memberLoginDto) {
        Member member = memberRepository.findByEmail(memberLoginDto.getEmail());
        Map map = new HashMap<String, Object>();
        if(member==null) {
            map.put("success", false);
            return map;
        }

        login(member);
        map.put("success", true);
        map.put("memberId", member.getId());
        return map;
    }
}
