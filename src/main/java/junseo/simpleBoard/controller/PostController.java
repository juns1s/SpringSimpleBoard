package junseo.simpleBoard.controller;

import junseo.simpleBoard.domain.Post;
import junseo.simpleBoard.repository.PostRepository;
import junseo.simpleBoard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
