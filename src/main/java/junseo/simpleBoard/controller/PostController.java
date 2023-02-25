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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "post/viewPost";
    }

    @GetMapping("postList/post/edit/{postId}")
    public String editPostForm(@AuthenticationPrincipal PrincipalDetail principal,
                               @PathVariable Long postId,
                               Model model){

        Member member = principal.getMember();

        try{
            postService.checkWriter(postId, member);
        }catch (IllegalStateException e){
            e.printStackTrace();
            return "redirect:/postList/post/{postId}";
        }

        model.addAttribute("postId", postId);
        model.addAttribute("postForm", new PostForm());
        return "post/editPost";
    }

    @PostMapping("postList/post/edit/{postId}")
    public String editPost(@AuthenticationPrincipal PrincipalDetail principal,
                           @PathVariable Long postId,
                           @ModelAttribute("post") @Valid PostForm form,
                           BindingResult result){

        if(result.hasErrors()){
            return "post/editPosts";
        }
        Long writerId = principal.getMember().getId();
        postService.editPost(postId, writerId, form);
        return "redirect:/postList/post/{postId}";
    }

    @PostMapping("postList/post/delete/{postId}")
    public String deletePost(@AuthenticationPrincipal PrincipalDetail principal, @PathVariable Long postId) {

        Member member = principal.getMember();

        try {
            postService.checkWriter(postId, member);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "redirect:/postList/post/{postId}";
        }

        postService.deletePost(postId);
        return "redirect:/postList";
    }
}
