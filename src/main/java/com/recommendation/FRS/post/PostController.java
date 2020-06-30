package com.recommendation.FRS.post;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletRequest;
import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 리스트
    @GetMapping("/posts/list")
    public HttpEntity<PagedResourcesAssembler<Post>> PostList(
            @Param(value = "title")String title, @RequestParam(name="page", defaultValue = "0")int page,
            PagedResourcesAssembler assembler) {
        Page<com.recommendation.FRS.post.PostMapping> postList = postService.PostList(page, title);
        return new ResponseEntity(assembler.toModel(postList), HttpStatus.OK);
    }

    // 게시글 등록
    @PostMapping("/posts")
    public ResponseEntity<Post> CreatePost(@RequestBody Map<String,String> post, ServletRequest request){
        Post newPost = postService.CreatePost(post, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPost.getId())
                .toUri();
        return ResponseEntity.created(location).body(newPost);
    }

    // 게시글 조회
    @GetMapping("/posts/{id}")
    public EntityModel<Post> FindPost(@PathVariable long id){
        Post loadPost = postService.LoadPost(id);
        EntityModel<Post> entityModel = new EntityModel<>(loadPost);
        entityModel.add(linkTo(methodOn(this.getClass()).UpdatePost(id,loadPost,null)).withRel("UpdateEndPoint"));
        entityModel.add(linkTo(methodOn(this.getClass()).DeletePost(id,null)).withRel("DeletePostEndPoint"));
        return entityModel;
    }

    // 게시글 업데이트
    @PatchMapping("/posts/{id}")
    public ResponseEntity<Post> UpdatePost(
            @PathVariable long id, @RequestBody Post post, ServletRequest request){
        Post posts = postService.UpdatePost(id,request,post);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Post> DeletePost(@PathVariable long id, ServletRequest request){
        postService.DeletePost(id,request);
        return new ResponseEntity(HttpStatus.OK);
    }

}
