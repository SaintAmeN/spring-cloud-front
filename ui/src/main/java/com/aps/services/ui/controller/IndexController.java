package com.aps.services.ui.controller;

import com.aps.services.ui.apiclients.ConfiglutMS;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class IndexController extends BaseAbstractController {
    private final ConfiglutMS configlutms;

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/test")
    public String getTest() {
        ResponseEntity responseEntity = configlutms.hello();
        System.out.println(responseEntity);

        return "redirect:/index";
    }
}
