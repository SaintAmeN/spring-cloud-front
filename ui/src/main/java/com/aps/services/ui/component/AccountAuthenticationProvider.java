package com.aps.services.ui.component;

import com.aps.services.config.UserJwtConfig;
import com.aps.services.model.AccountAuthentication;
import com.aps.services.model.dto.common.AbstractResponse;
import com.aps.services.model.dto.userservice.requests.AuthenticationRequestDto;
import com.aps.services.ui.apiclients.AuthorizationMS;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class AccountAuthenticationProvider extends DaoAuthenticationProvider {

    private final UserJwtConfig userJwtConfig;
    private final AuthorizationMS employeeService;

    @Override
    protected void doAfterPropertiesSet() throws Exception {
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
                () -> messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.onlySupports",
                        "Only UsernamePasswordAuthenticationToken is supported"));

        // Determine username
        String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
                : authentication.getName();

        boolean cacheWasUsed = true;
        UserDetails user = this.getUserCache().getUserFromCache(username);

        if (user == null) {
            cacheWasUsed = false;

            try {
                // todo: tutaj logowanie przez rest api
                ResponseEntity<HashMap<String, String>> response = employeeService.login(new AuthenticationRequestDto(String.valueOf(authentication.getPrincipal()), String.valueOf(authentication.getCredentials())));
//                AccountAuthentication accountAuthentication = new AccountAuthentication(response.g)
//                user =
//                user = retrieveUser(username,
//                        (UsernamePasswordAuthenticationToken) authentication);
                System.out.println(response);
            }
            catch (UsernameNotFoundException notFound) {
                logger.debug("User '" + username + "' not found");

                if (hideUserNotFoundExceptions) {
                    throw new BadCredentialsException(messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials",
                            "Bad credentials"));
                }
                else {
                    throw notFound;
                }
            }

            Assert.notNull(user,
                    "retrieveUser returned null - a violation of the interface contract");
        }

        try {
            getPreAuthenticationChecks().check(user);
            additionalAuthenticationChecks(user,
                    (UsernamePasswordAuthenticationToken) authentication);
        }
        catch (AuthenticationException exception) {
            if (cacheWasUsed) {
                // There was a problem, so try again after checking
                // we're using latest data (i.e. not from the cache)
                cacheWasUsed = false;
                user = retrieveUser(username,
                        (UsernamePasswordAuthenticationToken) authentication);
                getPreAuthenticationChecks().check(user);
                additionalAuthenticationChecks(user,
                        (UsernamePasswordAuthenticationToken) authentication);
            }
            else {
                throw exception;
            }
        }

        getPreAuthenticationChecks().check(user);

        if (!cacheWasUsed) {
            this.getUserCache().putUserInCache(user);
        }

        Object principalToReturn = user;

        if (isForcePrincipalAsString()) {
            principalToReturn = user.getUsername();
        }

        return createSuccessAuthentication(principalToReturn, authentication, user);
    }

}
