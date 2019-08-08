package com.amen.services.ui.filters;

import com.amen.services.ui.config.Constants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MessageInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true; // to allow passage
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String errorMessage = (String) request.getParameter(Constants.ERROR_MESSAGE);
        String message = (String) request.getParameter(Constants.MESSAGE);

        if (errorMessage != null) {
            modelAndView.addObject(Constants.ERROR_MESSAGE, errorMessage);
        }
        if (message != null) {
            modelAndView.addObject(Constants.MESSAGE, message);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
