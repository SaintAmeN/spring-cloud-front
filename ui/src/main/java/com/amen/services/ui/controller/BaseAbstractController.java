package com.amen.services.ui.controller;

import com.amen.services.ui.model.exception.usageerrors.UnauthorizedException;
import com.amen.services.ui.model.AccountAuthentication;
import com.amen.services.ui.model.dto.common.UserInfoRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author amen
 * @project ms-ui
 * @created 15.07.19
 */
public abstract class BaseAbstractController implements MessageSourceAware {
    protected MessageSource messageSource;

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

    @ModelAttribute(name = "currentUser")
    public UserInfoRequest user(Authentication authentication) {
        return UserInfoRequest.builder().id(Long.parseLong(userId(authentication))).name(username(authentication)).isAdmin(
                ((User) authentication.getPrincipal()).getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))
        ).build();
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
