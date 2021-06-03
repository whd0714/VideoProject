package videoproject.video.member.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import videoproject.video.member.MemberRepository;
import videoproject.video.member.dto.MemberLoginDto;

@Component
@RequiredArgsConstructor
public class LoginValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return MemberLoginDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MemberLoginDto memberLoginDto = (MemberLoginDto) o;


    }
}
