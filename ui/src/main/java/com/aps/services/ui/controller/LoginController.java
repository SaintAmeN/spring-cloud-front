package com.aps.services.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class LoginController {
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }
}