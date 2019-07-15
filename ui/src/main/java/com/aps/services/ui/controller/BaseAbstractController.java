package com.aps.services.ui.controller;

import com.aps.services.model.AccountAuthentication;
import com.aps.services.model.exception.usageerrors.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ModelAttribute;


/**
 * @author amen
 * @project ms-ui
 * @created 15.07.19
 */
public abstract class BaseAbstractController {

    @ModelAttribute(name = "username")
    public String username(Authentication authentication) {
        if (authentication == null) {
            throw new UnauthorizedException();
        }
        return String.valueOf(((User) authentication.getPrincipal()).getUsername());
    }

    @ModelAttribute(name = "userId")
    public String userId(Authentication authentication) {
        if (authentication == null) {
            throw new UnauthorizedException();
        }
        return String.valueOf(((AccountAuthentication) authentication.getPrincipal()).getUid());
    }

}
