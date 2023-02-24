package junseo.simpleBoard.controller;

import jakarta.validation.Valid;
import junseo.simpleBoard.PrincipalDetail;
import junseo.simpleBoard.domain.Member;
import junseo.simpleBoard.domain.Post;
import junseo.simpleBoard.repository.PostRepository;
import junseo.simpleBoard.service.PostForm;
import junseo.simpleBoard.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("postList/post/{postId}")
    public String readPost(@PathVariable Long postId, Model model){
        Post post = postRepository.findById(postId).get();
        model.addAttribute("post", post);
        return "post/view";
    }

    @GetMapping("postList/post/{postId}/edit")
    public String editPostForm(@AuthenticationPrincipal PrincipalDetail principal,@PathVariable Long postId ,Model model, BindingResult result){
        Member member = principal.getMember();
        try{
            postService.checkWriter(postId, member);
        }catch (IllegalStateException e){
            e.printStackTrace();
            result.reject("수정 실패", "작성자가 아닙니다.");
            return "postList/post/{postId}";
        }
        return "post/editPost";
    }

    @PostMapping("postList/post/{postId}/edit")
    public String editPost(@PathVariable Long postId, @ModelAttribute("post") @Valid PostForm form, BindingResult result){
        if(result.hasErrors()){
            return "post/editPosts";
        }
        Long writerId = postRepository.findById(postId).get().getMember().getId();
        postService.editPost(postId, writerId, form);
        return "redirect:/postList/post/{postId}";
    }
}
