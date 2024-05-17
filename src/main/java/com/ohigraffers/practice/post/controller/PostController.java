package com.ohigraffers.practice.post.controller;

import com.ohigraffers.practice.post.dto.request.PostCreateRequest;
import com.ohigraffers.practice.post.dto.request.PostUpdateRequest;
import com.ohigraffers.practice.post.dto.response.PostResponse;
import com.ohigraffers.practice.post.dto.response.ResponseMessage;
import com.ohigraffers.practice.post.model.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/* Swagger 문서화 시 Grouping 작성 */
@Tag(name = "post 관련 api")
@RestController
@RequestMapping("/posts")
public class PostController {

    private List<Post> posts;

    public PostController(){
        posts = new ArrayList<>();
        posts.add(new Post(1L, "제목1", "내용1", "홍길동"));
        posts.add(new Post(2L, "제목2", "내용2", "유관순"));
        posts.add(new Post(3L, "제목3", "내용3", "신사임당"));
        posts.add(new Post(4L, "제목4", "내용4", "이순신"));
        posts.add(new Post(5L, "제목5", "내용5", "장보고"));
    }

    /* 1. 전체 포스트 조회 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "전체 포스트 조회", description = "전체 포스트 목록을 조회한다.")
    @GetMapping("/posts")
    public ResponseEntity<ResponseMessage> findAllPosts() {

        /* 응답 데이터 설정 */
        /* Post 타입은 PostResponse 타입으로 변환해서 반환 */
        /* hateoas 적용 */
        List<EntityModel<PostResponse>> postWithRel = posts.stream().map(
                post ->
                        EntityModel.of(
                                PostResponse.from(post),
                                linkTo(methodOn(PostController.class).findPostByCode(post.getCode())).withSelfRel(),
                                linkTo(methodOn(PostController.class).findAllPosts()).withRel("posts")
                        )
        ).toList();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("posts", postWithRel);
        ResponseMessage responseMessage = new ResponseMessage(200,"조회 성공", responseMap);

        /* ResponseEntity 반환 */
        return ResponseEntity
                .ok()
                .body(responseMessage);
    }

    /* 2. 특정 코드로 포스트 조회 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "포스트 코드로 포스트 조회", description = "포스트 코드를 통해 해당하는 포스트 정보를 조회한다.")
    @GetMapping("/posts/{postCode}")
    public ResponseEntity<ResponseMessage> findPostByCode(@PathVariable Long postCode) {

        /* 응답 데이터 설정 */
        /* Post 타입은 PostResponse 타입으로 변환해서 반환 */
        /* hateoas 적용 */

        List<EntityModel<PostResponse>> postWithRel = posts.stream().filter(post -> post.getCode() == postCode).map(
                post ->
                        EntityModel.of(
                                PostResponse.from(post),
                                linkTo(methodOn(PostController.class).findPostByCode(post.getCode())).withSelfRel(),
                                linkTo(methodOn(PostController.class).findAllPosts()).withRel("posts")
                        )
        ).toList();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("post", postWithRel);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        /* ResponseEntity 반환 */
        return ResponseEntity
                .ok()
                .body(responseMessage);
    }

    /* 3. 신규 포스트 등록 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "신규 포스트 등록")
    @PostMapping("/posts")
   public ResponseEntity<Void> registPost(@RequestBody PostCreateRequest newPost) {

        /* 리스트에 추가 */
        Long lastPostCode = posts.get(posts.size() - 1).getCode();
        Long setCode = (lastPostCode + 1);

        String setTitle = newPost.getTitle();
        String setContent = newPost.getContent();
        String setWriter = newPost.getWriter();

        posts.add(new Post(setCode, setTitle, setContent, setWriter));

        /* ResponseEntity 반환 */
        return ResponseEntity
                .created(URI.create("/posts/posts/" + posts.get(posts.size() - 1).getCode()))
                .build();
   }

   /* 4. 포스트 제목과 내용 수정 */
   /* Swagger 문서화 시 설명 어노테이션 작성 */
   /* RequestMapping 어노테이션 작성 */
   @Operation(summary = "포스트 제목과 내용 수정")
   @PutMapping("/posts/{postCode}")
    public ResponseEntity<Void> modifyPost(@PathVariable Long postCode, @RequestBody PostUpdateRequest modifyPost) {

        /* 리스트에서 찾아서 수정 */
        /* 수정 메소드 활용 */
        Post foundPost = posts.stream().filter(post -> post.getCode() == postCode).toList().get(0);
        foundPost.modifyTitleAndContent(modifyPost.getTitle(), modifyPost.getContent());

        /* ResponseEntity 반환 */
       return ResponseEntity
               .created(URI.create("/posts/posts/" + postCode))
               .build();
    }

    /* 5. 포스트 삭제 */
    /* Swagger 문서화 시 설명 어노테이션 작성 */
    /* RequestMapping 어노테이션 작성 */
    @Operation(summary = "포스트 삭제")
    @DeleteMapping("/posts/{postCode}")
    public ResponseEntity<Void> removeUser(@PathVariable Long postCode) {

        /* 리스트에서 찾아서 삭제 */
//        PostResponse foundPost = PostResponse.from(posts.stream().filter(post -> post.getCode() == postCode).toList().get(0));
        Post foundPost = posts.stream().filter(post -> post.getCode() == postCode).toList().get(0);
        posts.remove(foundPost);

        /* ResponseEntity 반환 */
        return ResponseEntity
                .noContent()
                .build();
    }

}
