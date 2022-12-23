package com.example.kakaologinexample.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KaKaoController {

    @GetMapping("/index")
    public String index() {
        return "loginForm";
    }
}
