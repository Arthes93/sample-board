package com.example.sampleboard.control;

import com.example.sampleboard.domain.Post;
import com.example.sampleboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final PostService postService;

    @GetMapping("/")
    public String board(Model model){
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("PostList", posts);
        return "board";
    }
}
