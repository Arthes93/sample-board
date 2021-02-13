package com.example.sampleboard.service;

import com.example.sampleboard.control.dto.PostDto;
import com.example.sampleboard.domain.Post;
import com.example.sampleboard.exception.PostNotExistedException;
import com.example.sampleboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post addPost(PostDto postDto) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .name(postDto.getName())
                .content(postDto.getContent())
                .writeTime(LocalDateTime.now())
                .build();

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotExistedException(id));
        return post;
    }

    @Transactional
    public Post updatePost(PostDto postDto) {
        Post oldPost = postRepository.findById(postDto.getId()).orElseThrow(() -> new PostNotExistedException(postDto.getId()));
        oldPost.revise(postDto);
        return oldPost;
    }

    @Transactional
    public void deletePostById(Long id){
        postRepository.deleteById(id);
    }

    public Page<Post> getPosts(Pageable pageable) {
        int page;
        if(pageable.getPageNumber() <= 0){
            page = 0;
        }else{
            page = pageable.getPageNumber()-1;
        }

        Pageable requestPageable = PageRequest.of(page, pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        return postRepository.findAll(requestPageable);
    }
}
