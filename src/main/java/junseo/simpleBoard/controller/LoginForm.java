package junseo.simpleBoard.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 로그인을 위한 폼
 */
@Getter @Setter
public class LoginForm {
    @NotEmpty(message = "회원이름은 필수")
    private String userName;
    @NotEmpty(message = "비밀번호는 필수")
    private String password;
}
