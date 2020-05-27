package com.recommendation.FRS.user;

import com.recommendation.FRS.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public User join(Map<String, String> user){
        String passwordPattern ="((?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,})";
        // 중복 유저 체크
        Optional<User> findUser = userRepository.findByEmail(user.get("email"));
        if(findUser.isPresent()){
            throw new UserException.UserAlreadyExistException("User already existing");
        }

        // 패스워드  숫자, 문자, 특수문자 각각 1개 이상 포함
        Matcher matcher = Pattern.compile(passwordPattern).matcher(user.get("password"));
        if(!matcher.matches()){
            throw new IllegalArgumentException("Password not valid");
        }

        try {
                User createdUser = userRepository.save(User.builder()
                        .age(Long.parseLong(user.get("age")))
                        .nationality(user.get("nationality"))
                        .name(user.get("name"))
                    .email(user.get("email"))
                    .password(passwordEncoder.encode(user.get("password")))
                    .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                    .build());

            return createdUser;
        }catch (Exception e) {
            throw new IllegalArgumentException("The value is not valid");
        }
    }

    // 로그인 서비스 추가 필요
    public String Auth(Map<String,String> user){
        User findUser = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new UserException.UserNotFoundException("Unsubscribed Email"));
        if (!passwordEncoder.matches(user.get("password"), findUser.getPassword())) {
            throw new UserException.UserAuthenticationException("Invalid Password");
        }
        String token = jwtTokenProvider.createToken(findUser.getUsername(), findUser.getRoles());
        return token;
    }

    // 유저 정보 조회
    public User findUser(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UserException.UserNotFoundException(String.format("UserNotFoundException [%s]", email)));
        return user;
    }

    // 유저 업데이트
    public User UpdateUser(User user, User newUser){
        try{
            user.setAge(newUser.getAge());
            System.out.println(newUser.getAge());
            System.out.println(newUser.getNationality());
            user.setNationality(newUser.getNationality());
            user.setName(newUser.getName());
            User updateUser = userRepository.save(user);
            return updateUser;
        } catch(Exception e){
            throw new IllegalArgumentException("The value is not valid");
        }
    }

    // 유저 삭제
    public void DeleteUser(User user){
        userRepository.deleteById(user.getId());
    }


    // 유효한 토큰인 경우 토큰에서 유저 이메일 반환
    public String authUser(ServletRequest request){
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenProvider.validateToken(token)){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            String headerEmail = authentication.getName();
            return headerEmail;
        }
        return null;
    }
}
