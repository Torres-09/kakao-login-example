package com.example.kakaologinexample.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long createUser(String email) {
        User user = User.builder()
                .email(email)
                .build();

        userRepository.save(user);
        log.info("새로운 회원 저장 완료");
        return user.id;
    }
}
