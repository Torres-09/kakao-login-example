# 카카오 사용자 정보 가져오기

- Controller의 getUserInfo 함수를 통해 사용자 정보를 가져온다.
- `KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());`

```java
@RequiredArgsConstructor
@Component
public class KakaoUserInfo {
    private final WebClient webClient;
    private static final StringUSER_INFO_URI= "https://kapi.kakao.com/v2/user/me";
    private static final StringTARGET_ID_TYPE= "user_id";

    public KakaoUserInfoResponse getUserInfo(String token, long target_id) {
        String uri =USER_INFO_URI+ "?target_id_type=" +TARGET_ID_TYPE+ "&target_id=" + target_id;

        Flux<KakaoUserInfoResponse> response = webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);

        return response.blockFirst();
    }
}
```

AccessToken을 이용한 API 호출은 아래와 같은 헤더와 파라미터로 리턴을 얻을 수 있다.

### Header

| Name | Description | Required |
| --- | --- | --- |
| Authorization | 사용자 인증 수단, 액세스 토큰 값Authorization: Bearer ${ACCESS_TOKEN} | O |

### Parameter

| Name | Type | Description | Required |
| --- | --- | --- | --- |
| secure_resource | Boolean | 이미지 URL 값 HTTPS 여부, true 설정 시 HTTPS 사용, 기본 값 false | X |
| property_keys | String[] | Property 키 목록, JSON Array를 ["kakao_account.email"]과 같은 형식으로 사용 | X |
- 반환값은 아래와 같다.

### Response

| Name | Type | Description | Required |
| --- | --- | --- | --- |
| id | Long | 회원번호 | O |
| has_signed_up | Boolean | 자동 연결 설정을 비활성화한 경우만 존재연결하기 호출의 완료 여부false: 연결 대기(Preregistered) 상태true: 연결(Registered) 상태 | X |
| connected_at | Datetime | 서비스에 연결 완료된 시각, UTC* | X |
| synched_at | Datetime | 카카오싱크 간편가입을 통해 로그인한 시각, UTC* | X |
| properties | JSON | 사용자 프로퍼티(Property)https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#user-properties 참고 | X |
| kakao_account | https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#kakaoaccount | 카카오계정 정보 | X |

### KakaoAccount

| Name | Type | Description | Required |
| --- | --- | --- | --- |
| profile_needs_agreement | Boolean | 사용자 동의 시 프로필 정보(닉네임/프로필 사진) 제공 가능https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 프로필 정보(닉네임/프로필 사진) | X |
| profile_nickname_needs_agreement | Boolean | 사용자 동의 시 닉네임 제공 가능https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 닉네임 | X |
| profile_image_needs_agreement | Boolean | 사용자 동의 시 프로필 사진 제공 가능https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 프로필 사진 | X |
| https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#profile | JSON | 프로필 정보https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 프로필 정보(닉네임/프로필 사진), 닉네임, 프로필 사진 | X |
| name_needs_agreement | Boolean | 사용자 동의 시 카카오계정 이름 제공 가능https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 이름 | X |
| name | String | 카카오계정 이름https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 이름 | X |
| email_needs_agreement | Boolean | 사용자 동의 시 카카오계정 대표 이메일 제공 가능https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 카카오계정(이메일) | X |
| is_email_valid | Boolean | 이메일 유효 여부true: 유효한 이메일false: 이메일이 다른 카카오계정에 사용돼 만료https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 카카오계정(이메일) | X |
| is_email_verified | Boolean | 이메일 인증 여부true: 인증된 이메일false: 인증되지 않은 이메일https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 카카오계정(이메일) | X |
| email | String | 카카오계정 대표 이메일https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 카카오계정(이메일)비고: https://developers.kakao.com/docs/latest/ko/kakaologin/common#policy-email-caution | X |
| age_range_needs_agreement | Boolean | 사용자 동의 시 연령대 제공 가능https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 연령대 | X |
| age_range | String | 연령대1~9: 1세 이상 10세 미만10~14: 10세 이상 15세 미만15~19: 15세 이상 20세 미만20~29: 20세 이상 30세 미만30~39: 30세 이상 40세 미만40~49: 40세 이상 50세 미만50~59: 50세 이상 60세 미만60~69: 60세 이상 70세 미만70~79: 70세 이상 80세 미만80~89: 80세 이상 90세 미만90~: 90세 이상https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 연령대 | X |
| birthyear_needs_agreement | Boolean | 사용자 동의 시 출생 연도 제공 가능https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 출생 연도 | X |
| birthyear | String | 출생 연도(YYYY 형식)https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 출생 연도 | X |
| birthday_needs_agreement | Boolean | 사용자 동의 시 생일 제공 가능https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 생일 | X |
| birthday | String | 생일(MMDD 형식)https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 생일 | X |
| birthday_type | String | 생일 타입SOLAR(양력) 또는 LUNAR(음력)https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 생일 | X |
| gender_needs_agreement | Boolean | 사용자 동의 시 성별 제공 가능https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 성별 | X |
| gender | String | 성별female: 여성male: 남성https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 성별 | X |
| phone_number_needs_agreement | Boolean | 사용자 동의 시 전화번호 제공 가능https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 카카오계정(전화번호) | X |
| phone_number | String | 카카오계정의 전화번호국내 번호인 경우 +82 00-0000-0000 형식해외 번호인 경우 자릿수, 붙임표(-) 유무나 위치가 다를 수 있음(참고: https://github.com/google/libphonenumber)https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: 카카오계정(전화번호) | X |
| ci_needs_agreement | Boolean | 사용자 동의 시 CI 참고 가능https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: CI(연계정보) | X |
| ci | String | 연계정보https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: CI(연계정보) | X |
| ci_authenticated_at | Datetime | CI 발급 시각, UTC*https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item: CI(연계정보) | X |
- 회원의 AccessToken이외에도 어드민 키를 이용하여 회원 정보를 받을 수 있다. 해당 예제에서는 AccessToekn을 이용한 회원가입만을 다루고 있다.
- 이제 카카오 회원 정보 가져오기 API를 호출하여 가져왔으니, 이를 가지고 서비스 회원가입을 진행시킨다.

( Parameter, Response 출처 : [카카오 개발자 페이지](https://developers.kakao.com/docs/latest/ko/kakaologin/common) )