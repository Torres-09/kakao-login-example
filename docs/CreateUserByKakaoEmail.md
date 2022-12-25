# 소셜 이메일 정보로 서비스 회원가입하기

- 해당 예제에서 회원은 단지 이메일에 대한 정보를 가지고 있다. 예제라서 단순화했다.
- 컨트롤러에서 `userService.createUser(userInfo.getKakao_account().getEmail());` 가져온 이메일 정보를 기반으로 회원을 생성한다.

    ```java
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
    ```

- 이제 카카오 로그인부터, 인가 코드 받기, 토큰 받기, 해당 토큰으로 사용자 정보를 조회하여 서비스 회원가입하는 절차를 간단한 예제로 알아봤다. 이를 가지고 서비스 중복회원을 막거나 동의 내역 등의 다양한 서비스를 이용가능하다.