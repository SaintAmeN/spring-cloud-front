package com.amen.services.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }
}
