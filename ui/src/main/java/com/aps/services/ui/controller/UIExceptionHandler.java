package com.aps.services.ui.controller;

import com.aps.services.model.exception.usageerrors.UnauthorizedException;
import com.aps.services.model.exception.usageerrors.UsageException;
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

    @ExceptionHandler(UsageException.class)
    public String handleUsageException(UsageException ex, Model model) {
        model.addAttribute("status", "exception");
        model.addAttribute("errorMessage", "usage exception");
        model.addAttribute("errorException", ex.getMessage());
        return "error/error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleUnknownException(RuntimeException ex, Model model) {
        model.addAttribute("status", "exception");
        model.addAttribute("errorMessage", "unknown exception");
        model.addAttribute("errorException", ex.getMessage());
        return "error/error";
    }

}

