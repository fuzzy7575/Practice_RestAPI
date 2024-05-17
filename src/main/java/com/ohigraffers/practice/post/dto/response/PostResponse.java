package com.ohigraffers.practice.post.dto.response;

import com.ohigraffers.practice.post.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PostResponse {

    private Long code;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getCode(),
                post.getTitle(),
                post.getContent(),
                post.getWriter(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}
