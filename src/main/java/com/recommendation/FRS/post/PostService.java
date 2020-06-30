package com.recommendation.FRS.post;

import com.recommendation.FRS.exception.CustomException;
import com.recommendation.FRS.exception.UserException;
import com.recommendation.FRS.user.User;
import com.recommendation.FRS.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public Post CreatePost(Map<String, String> post, ServletRequest request) {
        String headerEmail = userService.authUser(request);
        try {
            User findUser = userService.findUser(headerEmail);
            Post newPost = postRepository.save(Post.builder()
                    .title(post.get("title"))
                    .content(post.get("content"))
                    .user(findUser)
                    .build());
            return newPost;
        } catch (Exception e) {
            throw new IllegalArgumentException("The value is not valid");
        }
    }

    public Post LoadPost(long id) {
        Post posts = postRepository.findById(id)
                .orElseThrow(() -> new CustomException.NotFoundException("Post Not Found"));
        return posts;
    }

    public Post UpdatePost(long id, ServletRequest request, Post updatePost) {
        String headerEmail = userService.authUser(request);
        Post posts = this.LoadPost(id);
        if (posts.getUser() != headerEmail) {
            throw new UserException.UserAuthenticationException("User not match");
        }
        try {
            posts.setTitle(updatePost.getTitle());
            posts.setContent(updatePost.getContent());
            Post successPost = postRepository.save(posts);
            return successPost;
        } catch (Exception e) {
            throw new IllegalArgumentException("The value is not valid");
        }
    }

    public void DeletePost(long id, ServletRequest request) {
        String headerEmail = userService.authUser(request);
        Post posts = this.LoadPost(id);
        if (posts.getUser() != headerEmail) {
            throw new UserException.UserAuthenticationException("User not match");
        }
        postRepository.deleteById(posts.getId());
        return;
    }

    public Page<PostMapping> PostList(int page, String title) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        if (title == null) {
            Page<PostMapping> postList = postRepository.findAllBy(pageRequest);
            System.out.println(postList.getTotalElements());
            return postList;
        } else {
            Page<PostMapping> postList = postRepository.findByTitleContaining(title, pageRequest);
            return postList;
        }

    }
}
