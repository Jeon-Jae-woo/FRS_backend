package com.recommendation.FRS.config;

import com.recommendation.FRS.user.User;
import com.recommendation.FRS.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(User.builder()
                .age(Long.parseLong("10"))
                .nationality("Korea")
                .name("ADMIN")
                .email("ADMIN_EMAIL")
                .gender("man")
                .password(passwordEncoder.encode("admin1!"))
                .roles(Collections.singletonList("ROLE_ADMIN"))
                .build());
    }
}
