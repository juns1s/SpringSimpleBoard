package junseo.simpleBoard.service;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostForm {

    @NotNull(message = "제목은 필수")
    private String title;
    @NotNull(message = "내용은 필수")
    private String content;
}
