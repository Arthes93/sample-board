package com.example.sampleboard.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/post")
    public String post(){
        return "post";
    }
}
