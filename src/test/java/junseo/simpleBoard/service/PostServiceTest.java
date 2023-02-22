package junseo.simpleBoard.service;

import jakarta.persistence.EntityManager;
import junseo.simpleBoard.controller.MemberForm;
import junseo.simpleBoard.domain.Member;
import junseo.simpleBoard.domain.Post;
import junseo.simpleBoard.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberService memberService;

    @Test
    public void 글_등록() throws Exception{
        //given
        MemberForm form = createMemberForm();
        Member member = memberService.join(form);

        PostInformation information= new PostInformation();
        information.setContent("hello world");
        information.setTitle("1st");
        //when
        Long postId = postService.post(member.getId(), information);
        //then
        Post post = postRepository.findOne(postId);
        assertEquals( member.getId(), post.getMember().getId(), "작성자 불일치");
        assertEquals("hello world", post.getContent(), "내용 불일치");
        assertEquals("1st", post.getTitle(), "제목 불일치");
        assertEquals(1, member.getPosts().size(), "작성한 글 추가");
    }

    @Test
    public void 글삭제() throws Exception{
        //given
        MemberForm form = createMemberForm();
        Member member = memberService.join(form);

        PostInformation information= new PostInformation();
        information.setContent("hello world");
        information.setTitle("1st");

        Long postId = postService.post(member.getId(), information);

        //when
        postService.deletePost(postId);

        //then
        org.assertj.core.api.Assertions.assertThat(postRepository.findOne(postId)).isNull();
    }

    @Test
    public void 글수정() throws Exception{
        //given
        MemberForm form = createMemberForm();
        Member member = memberService.join(form);

        PostInformation information= new PostInformation();
        information.setContent("hello world");
        information.setTitle("1st");

        PostInformation information2= new PostInformation();
        information.setContent("edited");
        information.setTitle("2nd");

        Long postId = postService.post(member.getId(), information);
        //when
        postService.editPost(postId, member.getId(), information2);
        //then
        assertEquals(postRepository.findOne(postId).getContent(), information2.getContent(),"콘텐츠가 다름");
        assertEquals(postRepository.findOne(postId).getTitle(), information2.getTitle(),"제목이 다름");
    }

    @Test
    public void 다른회원이글수정() throws Exception{
        //given
        MemberForm form = createMemberForm();
        Member member = memberService.join(form);

        MemberForm form2 = new MemberForm();
        form2.setPassword("123");
        form2.setNickName("111");
        form2.setUserName("444");
        Member member2 = memberService.join(form2);

        PostInformation information= new PostInformation();
        information.setContent("hello world");
        information.setTitle("1st");

        PostInformation information2= new PostInformation();
        information.setContent("edited");
        information.setTitle("2nd");

        Long postId = postService.post(member.getId(), information);

        //when
        assertThrows(IllegalStateException.class, ()->{
            postService.editPost(postId, member2.getId(), information2);
        });
        //then

    }

    private MemberForm createMemberForm() {
        MemberForm member = new MemberForm();
        member.setUserName("j");
        member.setPassword("aa");
        member.setNickName("jj");
        return member;
    }

}