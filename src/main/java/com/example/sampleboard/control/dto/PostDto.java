package com.example.sampleboard.control.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String name;

    @NotEmpty
    private String content;
}
