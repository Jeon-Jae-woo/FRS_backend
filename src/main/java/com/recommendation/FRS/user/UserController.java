package com.recommendation.FRS.user;

import com.recommendation.FRS.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletRequest;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 회원가입 엔드포인트
    @PostMapping("/join")
    public ResponseEntity<User> Join(@RequestBody Map<String, String> user){
        User createdUser = userService.join(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{email}")
                .buildAndExpand(createdUser.getEmail())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

    // 로그인 엔드포인트
    @PostMapping("/login")
    public Map<String,String> Login(@RequestBody Map<String, String> user) {
        Map<String,String> authResult = new HashMap<>();
        authResult.put("Authorization",userService.Auth(user));
        return authResult;
    }

    // 유저 정보 조회 엔드포인트
    @GetMapping("/users/{email}")
    public EntityModel<User> UserDetails(@PathVariable String email, ServletRequest request){
        String headerEmail = userService.authUser(request);
        User user = userService.findUser(email);

        if(user.getEmail() != headerEmail){
            throw new IllegalArgumentException("User not match");
        }

        // U,D 엔드포인트 제공
        EntityModel<User> entityModel = new EntityModel<>(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).UpdateUser(user.getEmail(),user,request));
        WebMvcLinkBuilder linkTo2 = linkTo(methodOn(this.getClass()).DeleteUser(user.getEmail(),request));
        entityModel.add(linkTo.withRel("updateUser"));
        entityModel.add(linkTo2.withRel("deleteUser"));

        return entityModel;
    }

    // 유저 정보 업데이트 엔드포인트
    @PatchMapping("/users/{email}")
    public ResponseEntity<User> UpdateUser(
            @PathVariable String email, @RequestBody User user ,ServletRequest request){
        String headerEmail = userService.authUser(request);
        User findUser = userService.findUser(email);
        if(findUser.getEmail() != headerEmail){
            throw new UserException.UserAuthenticationException("User not match");
        }

        userService.UpdateUser(findUser, user);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 유저 삭제 엔드포인트
    @DeleteMapping("/users/{email}")
    public ResponseEntity<User> DeleteUser(@PathVariable String email, ServletRequest request){
        String headerEmail = userService.authUser(request);
        User findUser = userService.findUser(email);
        if(findUser.getEmail() != headerEmail){
            throw new UserException.UserAuthenticationException("User not match");
        }
        userService.DeleteUser(findUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*
    // admin 추후 분리 예정
    @GetMapping("/admin/users/list")
    public List<User> adminUser(){
        List<User> allUser = userRepository.findAll();
        return allUser;
    }
    */

}