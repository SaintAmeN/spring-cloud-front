package com.aps.services.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class IndexController extends BaseAbstractController{

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }
}
