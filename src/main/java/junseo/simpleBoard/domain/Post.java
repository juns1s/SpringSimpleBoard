package junseo.simpleBoard.domain;

import jakarta.persistence.*;
import junseo.simpleBoard.service.PostForm;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 엔티티
 */
@Entity
@Table(name = "posts")
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String writer;  //member_nickName
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 연관관계 메서드
     */
    public void setMember(Member member){
        this.member = member;
    }

    /**
     * 게시글 수정 메소드
     * 자신이 작성한 게시글만 수정 가능함.
     */
    public Long edit(PostForm postInformation){
        this.title = postInformation.getTitle();
        this.content = postInformation.getContent();
        return this.getId();
    }
}
