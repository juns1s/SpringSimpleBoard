package junseo.simpleBoard.domain;

import lombok.Getter;

/**
 * 스프링 시큐리티에서의 사용을 위한 사용자 권한.
 */
@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
