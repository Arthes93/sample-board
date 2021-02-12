package com.example.sampleboard.service;

import com.example.sampleboard.domain.Post;
import com.example.sampleboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post addPost(Post newPost) {
        return postRepository.save(newPost);
    }
}
