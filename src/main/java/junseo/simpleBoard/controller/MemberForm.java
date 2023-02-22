package junseo.simpleBoard.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    @NotEmpty(message = "회원이름은 필수")
    private String userName;
    @NotEmpty(message = "별명은 필수")
    private String nickName;
    @NotEmpty(message = "비밀번호는 필수")
    private String password;
}
