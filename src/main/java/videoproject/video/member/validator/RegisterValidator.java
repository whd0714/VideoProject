package videoproject.video.member.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import videoproject.video.member.MemberRepository;
import videoproject.video.member.dto.MemberRegisterDto;

@Component
@RequiredArgsConstructor
public class RegisterValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return MemberRepository.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MemberRegisterDto memberRegisterDto = (MemberRegisterDto) o;

        if(memberRepository.existsByEmail(memberRegisterDto.getEmail())) {
            errors.rejectValue("email", "error.email", null, "이미 사용 중인 이메일입니다.");
        }
    }
}
