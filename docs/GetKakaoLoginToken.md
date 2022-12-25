# 카카오 로그인 토큰 받기

```java
@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoController {
    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final KakaoUserInfo kakaoUserInfo;

    private final UserService userService;

    @GetMapping("/index")
    public String index() {
        return "loginForm";
    }

    @Description("회원이 소셜 로그인을 마치면 자동으로 실행되는 API입니다. 인가 코드를 이용해 토큰을 받고, 해당 토큰으로 사용자 정보를 조회합니다." +
            "사용자 정보를 이용하여 서비스에 회원가입합니다.")
    @GetMapping("/oauth")
    @ResponseBody
    public String kakaoOauth(@RequestParam("code") String code) {
        log.info("인가 코드를 이용하여 토큰을 받습니다.");
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        log.info("토큰에 대한 정보입니다.{}",kakaoTokenResponse);
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());
        log.info("회원 정보 입니다.{}",userInfo);

        userService.createUser(userInfo.getKakao_account().getEmail());

        return "okay";
    }
}
```

- 유저가 소셜 로그인 페이지에서 로그인을 마치면 자동으로 Redirect_uri로 이동하여 API가 자동 호출된다. Param으로 code를 전달하여 해당 코드를 이용하여 `getToken` 함수를 호출하여 토큰 Response를 받는다.

    ```java
    // 인가코드를 이용하여 Token ( Access , Refresh )를 받는다.
    @Component
    @RequiredArgsConstructor
    public class KakaoTokenJsonData {
        private final WebClient webClient;
        private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
        private static final String REDIRECT_URI = "https://localhost:8080/oauth";
        private static final String GRANT_TYPE = "authorization_code";
        private static final String CLIENT_ID = "01ccede82258a2929766b26644beed99";
    
        public KakaoTokenResponse getToken(String code) {
            String uri = TOKEN_URI + "?grant_type=" + GRANT_TYPE + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&code=" + code;
            System.out.println(uri);
    
            Flux<KakaoTokenResponse> response = webClient.post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(KakaoTokenResponse.class);
    
            return response.blockFirst();
        }
    }
    ```

- 토큰을 받기위해서는 카카오 API를 호출해야 한다. 외부 API 를 호출하기 위해서는 feignClient, WebClient, RestTemplate 등을 사용할 수 있지만 예제에서는 WebClient를 이용했다.

  ### Parameter

  | Name | Type | Description | Required |
      | --- | --- | --- | --- |
  | grant_type | String | authorization_code로 고정 | O |
  | client_id | String | 앱 REST API 키[내 애플리케이션] > [앱 키]에서 확인 가능 | O |
  | redirect_uri | String | 인가 코드가 리다이렉트된 URI | O |
  | code | String | 인가 코드 받기 요청으로 얻은 인가 코드 | O |
  | client_secret | String | 토큰 발급 시, 보안을 강화하기 위해 추가 확인하는 코드[내 애플리케이션] > [보안]에서 설정 가능ON 상태인 경우 필수 설정해야 함 | X |

  ### Response

  | Name | Type | Description | Required |
      | --- | --- | --- | --- |
  | token_type | String | 토큰 타입, bearer로 고정 | O |
  | access_token | String | 사용자 액세스 토큰 값 | O |
  | id_token | String | https://developers.kakao.com/docs/latest/ko/kakaologin/common#oidc-id-token 값OpenID Connect 확장 기능을 통해 발급되는 ID 토큰, Base64 인코딩 된 사용자 인증 정보 포함제공 조건: https://developers.kakao.com/docs/latest/ko/kakaologin/common#oidc가 활성화 된 앱의 토큰 발급 요청인 경우또는 scope에 openid를 포함한 https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#additional-consent 요청을 거친 토큰 발급 요청인 경우 | X |
  | expires_in | Integer | 액세스 토큰과 ID 토큰의 만료 시간(초)참고: 액세스 토큰과 ID 토큰의 만료 시간은 동일 | O |
  | refresh_token | String | 사용자 리프레시 토큰 값 | O |
  | refresh_token_expires_in | Integer | 리프레시 토큰 만료 시간(초) | O |
  | scope | String | 인증된 사용자의 정보 조회 권한 범위범위가 여러 개일 경우, 공백으로 구분참고: https://developers.kakao.com/docs/latest/ko/kakaologin/common#oidc가 활성화된 앱의 토큰 발급 요청인 경우, ID 토큰이 함께 발급되며 scope 값에 openid 포함 | X |

  파라미터와 반환 값으로는 다음과 같은 Value들이 있다.

( Parameter, Response 출처 : [카카오 개발자 페이지](https://developers.kakao.com/docs/latest/ko/kakaologin/common) )