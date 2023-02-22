package junseo.simpleBoard.service;

import junseo.simpleBoard.controller.MemberForm;
import junseo.simpleBoard.domain.Member;
import junseo.simpleBoard.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        MemberForm form = new MemberForm();
        form.setPassword("1");
        form.setUserName("1");
        form.setNickName("1");
        //when
        Member member   = memberService.join(form);
        //then
        Assertions.assertThat(member.getUserName()).isEqualTo(form.getUserName());
    }

    @Test()
    public void 중복회원() throws Exception{
        //given
        MemberForm form = new MemberForm();
        form.setPassword("1234");
        form.setUserName("1");
        form.setNickName("1");
        MemberForm form2 = new MemberForm();
        form2.setPassword("1234");
        form2.setUserName("1");
        form2.setNickName("1");
        //when
        memberService.join(form);

        //then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(form2);
        });
    }
}