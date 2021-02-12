package com.example.sampleboard.control;

import com.example.sampleboard.control.dto.PostDto;
import com.example.sampleboard.domain.Post;
import com.example.sampleboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public String post(@ModelAttribute("postDto")PostDto postDto){
        return "post";
    }


    @PostMapping("/post")
    public String creatNewPost(@Valid @ModelAttribute("postDto") PostDto postDto){
        Post newPost = postService.addPost(Post.builder()
                .title(postDto.getTitle())
                .name(postDto.getName())
                .content(postDto.getContent())
                .writeTime(LocalDateTime.now())
                .build());

        return "redirect:post";
    }
}
