package com.example.kakaologinexample.api;

import com.example.kakaologinexample.domain.User;
import com.example.kakaologinexample.domain.UserRepository;
import com.example.kakaologinexample.domain.UserService;
import com.example.kakaologinexample.utils.KakaoTokenJsonData;
import com.example.kakaologinexample.utils.KakaoUserInfo;
import com.example.kakaologinexample.utils.dto.KakaoTokenResponse;
import com.example.kakaologinexample.utils.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoTokenJsonData kakaoTokenJsonData;
    private final KakaoUserInfo kakaoUserInfo;

    private final UserService userService;

    @GetMapping("/index")
    public String index() {
        return "loginForm";
    }

    @GetMapping("/oauth")
    @ResponseBody
    public String kakaoOauth(@RequestParam("code") String code) {
        System.out.println("호출");
        KakaoTokenResponse kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        System.out.println(kakaoTokenResponse);
        KakaoUserInfoResponse userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token(), 1);
        System.out.println(userInfo);

        userService.createUser(userInfo.getKakao_account().getEmail());

        // 됐다.~
        return "okay";
    }
}
