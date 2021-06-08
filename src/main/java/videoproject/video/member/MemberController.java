package videoproject.video.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import videoproject.video.member.dto.MemberLoginDto;
import videoproject.video.member.dto.MemberRegisterDto;
import videoproject.video.member.validator.RegisterValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final RegisterValidator registerValidator;

    /*@InitBinder("memberRegisterDto")
    public void registerBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(registerValidator);
    }*/

    @PostMapping("/api/register")
    public Map register(@Valid @RequestBody MemberRegisterDto memberRegisterDto, Errors errors) {
        Map map = new HashMap<String, Object>();
        if(errors.hasErrors()) {
            map.put("success", false);
            return map;
        }

        memberService.register(memberRegisterDto);
        map.put("success", true);
        return map;
    }

    @PostMapping("/api/login")
    public Map login(@RequestBody MemberLoginDto memberLoginDto, Errors errors) {
        System.out.println("@@@@" + memberLoginDto);
        Map map = new HashMap<String, Object>();
        if(errors.hasErrors()) {
            map.put("success", false);
            return map;
        }

        map = memberService.loginProcess(memberLoginDto);

        return map;
    }

    @GetMapping("/api/auth")
    public Result authMember(@CurrentUser Member member) {
        if (member == null) {
            Map map = new HashMap<String, Object>();
            map.put("success", false);
            return new Result(map);
        }
        AuthMember authMember = new AuthMember(member.getName(), member.getEmail(), member.isAdmin(), member.getJoinAt());
        return new Result(authMember);
    }

    @GetMapping("/api/logout")
    public Map logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        Map map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    static class AuthMember {
        private String name;
        private String email;
        private boolean isAdmin;
        private LocalDateTime joinAt;
        private boolean success;

        public AuthMember(String name, String email, boolean admin, LocalDateTime joinAt) {
            this.name = name;
            this.email = email;
            this.isAdmin = admin;
            this.joinAt = joinAt;
            this.success = true;
        }
    }

}
