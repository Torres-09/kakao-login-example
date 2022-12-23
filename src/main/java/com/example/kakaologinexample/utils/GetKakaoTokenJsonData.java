package com.example.kakaologinexample.utils;

import com.example.kakaologinexample.utils.dto.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class GetKakaoTokenJsonData {
    private final WebClient webClient;
    private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String REDIRECT_URI = "https://localhost:8080/oauth";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String CLIENT_ID = "01ccede82258a2929766b26644beed99";

    public KakaoTokenResponse getToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity request = new HttpEntity(headers);

//        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(TOKEN_URI)
//                .queryParam("grant_type", GRANT_TYPE)
//                .queryParam("client_id", CLIENT_ID)
//                .queryParam("redirect_uri", REDIRECT_URI)
//                .queryParam("code", code);

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
