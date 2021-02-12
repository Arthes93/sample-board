package com.example.sampleboard.control;

import com.example.sampleboard.control.dto.PostDto;
import com.example.sampleboard.domain.Post;
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

        given(postService.addPost(mockPost)).willReturn(mockPost);

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

        given(postService.addPost(mockPost)).willReturn(mockPost);

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

        given(postService.addPost(mockPost)).willReturn(mockPost);

        ResultActions resultActions = mockMvc.perform(post("/post")
                .flashAttr("postDto", new PostDto())
                .param("title", "test")
                .param("name", "tester")
        );

        resultActions
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}