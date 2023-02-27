package junseo.simpleBoard.repository;

import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 검색을 위한 클래스
 * 추후 검색 구현예정.
 */
@Getter @Setter
public class PostSearch {
    private String writer;
    private String title;
}
