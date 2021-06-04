package videoproject.video.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import videoproject.video.member.dto.MemberLoginDto;
import videoproject.video.member.dto.MemberRegisterDto;
import videoproject.video.member.validator.RegisterValidator;

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
            return null;
        }
        AuthMember authMember = new AuthMember(member.getName(), member.getEmail(), member.isAdmin(), member.getJoinAt());
        return new Result(authMember);
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
