package junseo.simpleBoard.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import junseo.simpleBoard.domain.Member;
import junseo.simpleBoard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/joinMember")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "member/createMemberForm";
    }

    @PostMapping("/joinMember")
    public String create(@Valid MemberForm form, BindingResult result){
        if(result.hasErrors()){
            return "member/createMemberForm";
        }
        try{
            memberService.join(form);
        }catch(IllegalStateException e){
            e.printStackTrace();
            result.reject("회원가입 실패", "이미 등록된 사용자입니다.");
            return "member/createMemberForm";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "/member/memberLoginForm";
    }
}