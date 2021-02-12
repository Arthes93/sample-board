package com.example.sampleboard.service;

import com.example.sampleboard.domain.Post;
import com.example.sampleboard.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    private PostService postService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        postService = new PostService(postRepository);
    }

    @Test
    @DisplayName("새로운 포스트를 추가한다.")
    @Transactional
    public void addPost() {
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

    @Test
    @DisplayName("모든 포스트를 가져온다.")
    public void getAllPosts() {
        List<Post> mockPosts = new ArrayList<>();
        mockPosts.add(Post.builder()
                .id(1L)
                .title("test")
                .name("tester")
                .content("test")
                .writeTime(LocalDateTime.now())
                .build()
        );

        given(postRepository.findAll()).willReturn(mockPosts);

        List<Post> posts = postService.getAllPosts();

        verify(postRepository).findAll();

        Post post = posts.get(0);
        assertThat(post.getId()).isEqualTo(mockPosts.get(0).getId());
        assertThat(post.getTitle()).isEqualTo(mockPosts.get(0).getTitle());
        assertThat(post.getName()).isEqualTo(mockPosts.get(0).getName());
        assertThat(post.getContent()).isEqualTo(mockPosts.get(0).getContent());
        assertThat(post.getWriteTime()).isEqualTo(mockPosts.get(0).getWriteTime());

    }
}