package com.example.sampleboard.control;

import com.example.sampleboard.control.dto.PostDto;
import com.example.sampleboard.domain.Post;
import com.example.sampleboard.exception.PostNotExistedException;
import com.example.sampleboard.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    @DisplayName("게시글 작성페이지로 이동하는지 확인한다.")
    public void movePost() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/post"));

        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("새로운 게시글 생성을 성공한다.")
    public void successCreateNewPost() throws Exception {
        Post mockPost = Post.builder()
                .id(1L)
                .title("test")
                .name("tester")
                .content("test")
                .writeTime(LocalDateTime.now())
                .build();

        given(postService.addPost(any())).willReturn(mockPost);

        ResultActions resultActions = mockMvc.perform(post("/post")
                        .flashAttr("postDto", new PostDto())
//                .sessionAttr("postDto", new PostDto())
                        .param("title", "test")
                        .param("name", "tester")
                        .param("content", "test")
        );

        resultActions
                .andExpect(status().is3xxRedirection())
                // Response Header에 있는 Location 정보를 비교한다.
                .andDo(print());

        verify(postService).addPost(any());
    }

    @Test
    @DisplayName("새로운 게시글 생성을 실패한다.")
    public void failCreateNewFailWithNoTitle() throws Exception {
        Post mockPost = Post.builder()
                .id(1L)
                .title("test")
                .name("tester")
                .content("test")
                .writeTime(LocalDateTime.now())
                .build();

        given(postService.addPost(any())).willReturn(mockPost);

        ResultActions resultActions = mockMvc.perform(post("/post")
                .flashAttr("postDto", new PostDto())
                .param("name", "tester")
                .param("content", "test")
        );

        resultActions
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("새로운 게시글 생성을 실패한다.")
    public void failCreateNewFailWithNoName() throws Exception {
        Post mockPost = Post.builder()
                .id(1L)
                .title("test")
                .name("tester")
                .content("test")
                .writeTime(LocalDateTime.now())
                .build();

        given(postService.addPost(any())).willReturn(mockPost);

        ResultActions resultActions = mockMvc.perform(post("/post")
                .flashAttr("postDto", new PostDto())
                .param("title", "test")
                .param("content", "test")
        );

        resultActions
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("새로운 게시글 생성을 실패한다.")
    public void failCreateNewFailWithNoContent() throws Exception {
        Post mockPost = Post.builder()
                .id(1L)
                .title("test")
                .name("tester")
                .content("test")
                .writeTime(LocalDateTime.now())
                .build();

        given(postService.addPost(any())).willReturn(mockPost);

        ResultActions resultActions = mockMvc.perform(post("/post")
                .flashAttr("postDto", new PostDto())
                .param("title", "test")
                .param("name", "tester")
        );

        resultActions
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("상세 페이지를 가져온다.")
    public void getPostDetails() throws Exception {
        Post mockPost = Post.builder()
                .id(1L)
                .name("tester")
                .title("test")
                .content("test")
                .writeTime(LocalDateTime.now())
                .build();

        given(postService.getPostById(any())).willReturn(mockPost);
        ResultActions resultActions = mockMvc.perform(get("/post/1"));
        resultActions
                .andExpect(status().isOk());

        verify(postService).getPostById(any());
    }

    @Test
    @DisplayName("상세 페이지를 못 가져온다.")
    public void getPostDetailsException() throws Exception {
        given(postService.getPostById(any())).willThrow(new PostNotExistedException(1L));

        ResultActions resultActions = mockMvc.perform(get("/post/1"));

        resultActions
                .andExpect(status().isNotFound());

        verify(postService).getPostById(any());
    }

    @Test
    @DisplayName("포스트 수정 페이지로 이동한다.")
    public void getPostDetailsToRevise() throws Exception {
        Post mockPost = Post.builder()
                .id(1L)
                .name("tester")
                .title("test")
                .content("test")
                .writeTime(LocalDateTime.now())
                .build();

        given(postService.getPostById(1L)).willReturn(mockPost);
        ResultActions resultActions = mockMvc.perform(get("/post/1/revise"));
        verify(postService).getPostById(1L);

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("포스트를 수정한다.")
    public void creatOrUpdatePost() throws Exception {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .name("tester")
                .title("test")
                .content("test")
                .build();

        ResultActions resultActions = mockMvc.perform(post("/post")
                .flashAttr("postDto", new PostDto())
                .param("id", "1")
                .param("name", "tester")
                .param("title", "test")
                .param("content", "test")
        );

        verify(postService).updatePost(postDto);

        resultActions
                .andExpect(status().is3xxRedirection())
                .andDo(print())
        ;
    }

    @Test @DisplayName("포스트를 삭제한다.")
    public void deletePost() throws Exception{

    }
}