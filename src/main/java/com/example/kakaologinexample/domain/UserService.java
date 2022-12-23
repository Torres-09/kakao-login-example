package com.example.kakaologinexample.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long createUser(String email) {
        User user = User.builder()
                .email(email)
                .build();

        userRepository.save(user);
        System.out.println("유저 저장 완료");
        return user.id;
    }
}
