package com.example.kakaologinexample.utils;

import com.example.kakaologinexample.utils.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class KakaoUserInfo {
    private final WebClient webClient;
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    private static final String TARGET_ID_TYPE = "user_id";

    public KakaoUserInfoResponse getUserInfo(String token, long target_id) {
        String uri = USER_INFO_URI + "?target_id_type=" + TARGET_ID_TYPE + "&target_id=" + target_id;

        Flux<KakaoUserInfoResponse> response = webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);

        return response.blockFirst();
    }
}
