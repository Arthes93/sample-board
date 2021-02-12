package com.example.sampleboard;

import com.example.sampleboard.domain.Post;
import com.example.sampleboard.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class SampleBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleBoardApplication.class, args);
    }


    @Bean
    public CommandLineRunner runner(PostRepository postRepository) throws Exception {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                IntStream.rangeClosed(1, 200).forEach(index ->
                        postRepository.save(Post.builder()
                                .title("게시글" + index)
                                .name("tester")
                                .content("내용" + index)
                                .writeTime(LocalDateTime.now())
                                .build()));
            }
        };
    }
}
