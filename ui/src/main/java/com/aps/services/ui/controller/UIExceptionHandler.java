package com.aps.services.ui.controller;

import com.aps.services.model.exception.usageerrors.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author amen
 * @project ms-ui
 * @created 15.07.19
 */
@ControllerAdvice
@Controller
public class UIExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    public String handleNPE(NullPointerException npe, Model model) {
        npe.printStackTrace();
        model.addAttribute("status", "exception");
        model.addAttribute("errorMessage", "npe");
        model.addAttribute("errorException", String.valueOf(npe.getStackTrace()));
        return "error/error";
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorized(UnauthorizedException une, Model model) {

        return "redirect:/user/login";
    }
}

