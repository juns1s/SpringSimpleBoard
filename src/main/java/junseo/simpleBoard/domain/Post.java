package junseo.simpleBoard.domain;

import jakarta.persistence.*;
import junseo.simpleBoard.service.PostInformation;
import lombok.Getter;
import lombok.Setter;

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
        member.getPosts().add(this);    //작성 리스트에 추가.
    }

    /**
     * 게시글 수정 메소드
     * 자신이 작성한 게시글만 수정 가능함.
     */
    public Long edit(PostInformation postInformation){
        this.title = postInformation.getTitle();
        this.content = postInformation.getContent();
        return this.getId();
    }

    /**
     * 게시글 삭제 메소드
     * 자신이 작성한 게시글만 삭제 가능함.
     */
    public void delete(){
        //작성 목록에서 삭제
        member.getPosts().remove(this);
    }
}
