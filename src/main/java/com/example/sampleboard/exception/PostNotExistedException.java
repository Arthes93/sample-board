package com.example.sampleboard.exception;

public class PostNotExistedException extends RuntimeException{
    public PostNotExistedException(Long id){
        super("Post id : " + id + " is not Existed");

    }
}
