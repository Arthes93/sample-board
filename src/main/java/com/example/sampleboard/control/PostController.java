package com.example.sampleboard.control;

import com.example.sampleboard.control.dto.PostDto;
import com.example.sampleboard.domain.Post;
import com.example.sampleboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String post(@ModelAttribute("postDto") PostDto postDto) {
        return "post";
    }


    @PostMapping("/post")
    public String creatNewPost(@Valid @ModelAttribute("postDto") PostDto postDto) {
        if(postDto.getId() == null) {
            postService.addPost(postDto);
        }else{
            postService.revisePostDetails(postDto);
        }

        return "redirect:/";
    }

    @GetMapping("/post/{postId}")
    public String getPostDetails(@PathVariable("postId") Long id, Model model) {
        Post post = postService.getPostById(id);

        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .name(post.getName())
                .title(post.getTitle())
                .content(post.getContent())
                .writeTime(post.getWriteTime())
                .build();

        model.addAttribute("postDto", postDto);
        return "details";
    }


    @GetMapping("/post/{postId}/revise")
    public String getPostDetailsToRevise(@PathVariable("postId") Long id, Model model) {
        Post post = postService.getPostById(id);

        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .name(post.getName())
                .title(post.getTitle())
                .content(post.getContent())
                .writeTime(post.getWriteTime())
                .build();

        model.addAttribute("postDto", postDto);
        return "post";
    }

//    @PatchMapping("/post/{postId}/revise")
//    public String revisePostDetails(
//            @PathVariable("postId") Long id
//            , @Valid @ModelAttribute("postDto") PostDto postDto
//    ) {
//        Post newPost = Post.builder()
//                .id(postDto.getId())
//                .name(postDto.getName())
//                .title(postDto.getTitle())
//                .writeTime(postDto.getWriteTime())
//                .content(postDto.getContent())
//                .build();
//
//        Post post = postService.revisePostDetails(newPost);
//
//        return "redirect:/post/"+post.getId();
//    }

}
