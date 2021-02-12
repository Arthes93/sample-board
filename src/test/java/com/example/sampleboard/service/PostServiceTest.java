package com.example.sampleboard.service;

import com.example.sampleboard.domain.Post;
import com.example.sampleboard.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    private PostService postService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        postService = new PostService(postRepository);
    }

    @Test
    @DisplayName("새로운 포스트를 추가한다.")
    public void addPost(){
        Post post = Post.builder()
                .title("test")
                .name("tester")
                .content("test")
                .writeTime(LocalDateTime.now())
                .build();

        Post mockPost = Post.builder()
                .id(1L)
                .title("test")
                .name("tester")
                .content("test")
                .writeTime(LocalDateTime.now())
                .build();

        given(postRepository.save(post)).willReturn(mockPost);

        Post returnPost = postService.addPost(post);

        assertThat(returnPost.getId()).isEqualTo(mockPost.getId());
        assertThat(returnPost.getTitle()).isEqualTo(mockPost.getTitle());
        assertThat(returnPost.getName()).isEqualTo(mockPost.getName());
        assertThat(returnPost.getContent()).isEqualTo(mockPost.getContent());
        assertThat(returnPost.getWriteTime()).isEqualTo(mockPost.getWriteTime());


        verify(postRepository).save(post);
    }
}