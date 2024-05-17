package com.ohigraffers.practice.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/* Swagger 문서화 시 설명 어노테이션 작성 */
@Schema(description = "포스트 등록 DTO")
public class PostCreateRequest {

    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* 필수 값이므로 유효성 검사 어노테이션 작성 */
    @Schema(description = "포스트 제목")
    @NotNull(message = "제목은 반드시 입력되어야 합니다.")
    private String title;

    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* 필수 값이므로 유효성 검사 어노테이션 작성 */
    @Schema(description = "포스트 내용")
    @NotNull(message = "내용은 반드시 입력되어야 합니다.")
    private String content;

    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* 필수 값이므로 유효성 검사 어노테이션 작성 */
    @Schema(description = "포스트 작성자")
    @NotNull(message = "작성자는 반드시 입력되어야 합니다.")
    private String writer;

}

