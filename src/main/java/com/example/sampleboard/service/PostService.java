package com.example.sampleboard.service;

import com.example.sampleboard.domain.Post;
import com.example.sampleboard.exception.PostNotExistedException;
import com.example.sampleboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post addPost(Post newPost) {
        return postRepository.save(newPost);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotExistedException(id));
        return post;
    }
}
