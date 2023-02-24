package junseo.simpleBoard.controller;

import jakarta.validation.Valid;
import junseo.simpleBoard.PrincipalDetail;
import junseo.simpleBoard.domain.Post;
import junseo.simpleBoard.repository.PostRepository;
import junseo.simpleBoard.service.PostForm;
import junseo.simpleBoard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping("/postList")
    public String posts(Model model){
        List<Post> postList = postRepository.findAll();
        model.addAttribute("postList", postList);
        return "post/postList";
    }

    @GetMapping("/postList/newPost")
    public String createPostForm(@AuthenticationPrincipal PrincipalDetail principal,Model model){
        if(principal==null){
            return "redirect:/login";
        }
        model.addAttribute("postForm", new PostForm());
        return "/post/createPostForm";
    }

    @PostMapping("/postList/newPost")
    public String newPost(@AuthenticationPrincipal PrincipalDetail principal, @Valid PostForm form, BindingResult result){
        if(result.hasErrors()){
            return "member/createPostForm";
        }
        postService.post(principal.getMember().getId(),form);
        return "redirect:/postList";
    }
}
