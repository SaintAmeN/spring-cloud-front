package com.aps.services.ui.component;

import com.aps.services.config.UserJwtConfig;
import com.aps.services.model.AccountAuthentication;
import com.aps.services.model.dto.common.AbstractResponse;
import com.aps.services.model.dto.userservice.requests.AuthenticationRequestDto;
import com.aps.services.model.dto.userservice.responses.AuthenticationResponse;
import com.aps.services.ui.apiclients.AuthorizationMS;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountAuthenticationProvider extends DaoAuthenticationProvider {

    private final UserJwtConfig userJwtConfig;
    private final AuthorizationMS employeeService;
    private final ObjectMapper objectMapper;

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
                ResponseEntity<String> response = employeeService.login(new AuthenticationRequestDto(String.valueOf(authentication.getPrincipal()), String.valueOf(authentication.getCredentials())));
                try {
                    AuthenticationResponse authResponse = objectMapper.readValue(response.getBody(), AuthenticationResponse.class);

                    System.out.println(authResponse);
                    List<String> authHeaders = response.getHeaders().get(userJwtConfig.getHeader());
                    Assert.notEmpty(authHeaders, "Header should be returned with authentication data.");

                    user = new AccountAuthentication(
                            authResponse.getUsername(),
                            authResponse.getPassword(),
                            authResponse.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                            authHeaders.get(0));

                } catch (IOException e) {
                    logger.error("Error while trying to decode message.");
                }
            } catch (UsernameNotFoundException notFound) {
                logger.debug("User '" + username + "' not found");

                if (hideUserNotFoundExceptions) {
                    throw new BadCredentialsException(messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials",
                            "Bad credentials"));
                } else {
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
        } catch (AuthenticationException exception) {
            if (cacheWasUsed) {
                // There was a problem, so try again after checking
                // we're using latest data (i.e. not from the cache)
                cacheWasUsed = false;
                user = retrieveUser(username,
                        (UsernamePasswordAuthenticationToken) authentication);
                getPreAuthenticationChecks().check(user);
                additionalAuthenticationChecks(user,
                        (UsernamePasswordAuthenticationToken) authentication);
            } else {
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
