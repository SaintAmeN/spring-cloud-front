package com.aps.services.ui.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.aps.services.ui.config.Constants.ERROR_MESSAGE;
import static com.aps.services.ui.config.Constants.MESSAGE;

@Component
public class MessageInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true; // to allow passage
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String errorMessage = (String) request.getParameter(ERROR_MESSAGE);
        String message = (String) request.getParameter(MESSAGE);

        if (errorMessage != null) {
            modelAndView.addObject(ERROR_MESSAGE, errorMessage);
        }
        if (message != null) {
            modelAndView.addObject(MESSAGE, message);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
