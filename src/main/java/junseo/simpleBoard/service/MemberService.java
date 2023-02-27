package junseo.simpleBoard.service;

import junseo.simpleBoard.controller.MemberForm;
import junseo.simpleBoard.domain.Member;
import junseo.simpleBoard.domain.UserRole;
import junseo.simpleBoard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    @Transactional
    public Member join(MemberForm form)throws IllegalStateException{
        validateDuplicateMember(form);

        Member member = new Member();
        member.setUserName(form.getUserName());
        if(form.getUserName()=="admin"){
            member.setRole(UserRole.ADMIN);
        } else{
          member.setRole(UserRole.USER);
        }
        member.setNickName(form.getNickName());
        member.setPassword(passwordEncoder.encode(form.getPassword()));
        System.out.println(member.getPassword());

        memberRepository.save(member);
        return member;
    }

    /**
     * 아이디로 조회
     */
    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).get();
    }

    /**
     * 회원가입시 중복회원 검증
     */
    private void validateDuplicateMember(MemberForm form) {
        if(!memberRepository.findByuserName(form.getUserName()).isEmpty())
            throw new IllegalStateException("이미 존재하는 회원.");
    }
}
