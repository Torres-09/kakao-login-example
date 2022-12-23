package com.example.kakaologinexample.api;

import com.example.kakaologinexample.utils.GetKakaoTokenJsonData;
import com.example.kakaologinexample.utils.dto.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class KakaoController {
    private final GetKakaoTokenJsonData kakaoTokenJsonData;

    @GetMapping("/index")
    public String index() {
        return "loginForm";
    }

    @GetMapping("/oauth")
    public String kakaoOauth(@RequestParam("code") String code) {
        System.out.println("호출");
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        System.out.println(kakaoTokenResponse);
        // 됐다.~
        return null;
    }
}
