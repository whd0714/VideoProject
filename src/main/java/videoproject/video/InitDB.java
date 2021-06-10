package videoproject.video;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import videoproject.video.member.Member;
import videoproject.video.member.MemberRepository;
import videoproject.video.member.dto.MemberRegisterDto;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.db1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final PasswordEncoder passwordEncoder;
        private final MemberRepository memberRepository;
        public void db1() {

            String password = passwordEncoder.encode("12345678");
            Member member1 = new Member("이름", "aa@naver.com", password);
            memberRepository.save(member1);


        }
    }
}
