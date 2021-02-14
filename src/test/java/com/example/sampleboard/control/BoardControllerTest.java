package com.example.sampleboard.control;

import com.example.sampleboard.domain.Post;
import com.example.sampleboard.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    @DisplayName("모든 Post를 가져온다.")
    public void board() throws Exception {
        List<Post> posts = new ArrayList<>();
        posts.add(Post.builder()
                .id(1L)
                .title("test")
                .name("tester")
                .content("test")
                .writeTime(LocalDateTime.now())
                .build()
        );

        Page<Post> pagePosts = new PageImpl<>(posts);

        PageRequest pageRequest = PageRequest.of(1, 10);


        given(postService.getPosts(pageRequest)).willReturn(pagePosts);
        ResultActions resultActions = mockMvc.perform(get("/")
                .param("page", "1")
//                .flashAttr("PostList", new ArrayList<>())
        );

        resultActions
                .andExpect(status().isOk())
                .andDo(print());
        verify(postService).getPosts(pageRequest);
    }

}