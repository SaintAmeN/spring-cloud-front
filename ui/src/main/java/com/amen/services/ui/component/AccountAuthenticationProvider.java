package com.amen.services.ui.component;

import com.amen.services.ui.config.UserJwtConfig;
import com.amen.services.ui.model.dto.userservice.requests.AuthenticationRequestDto;
import com.amen.services.ui.model.AccountAuthentication;
import com.amen.services.ui.apiclients.AuthorizationMS;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountAuthenticationProvider extends DaoAuthenticationProvider {
    private final UserJwtConfig userJwtConfig;
    private final AuthorizationMS employeeService;
    private final TokenParserUtility tokenParserUtility;

    public AccountAuthenticationProvider(UserJwtConfig userJwtConfig, AuthorizationMS employeeService, TokenParserUtility tokenParserUtility, BCryptPasswordEncoder passwordEncoder) {
        this.userJwtConfig = userJwtConfig;
        this.employeeService = employeeService;
        this.tokenParserUtility = tokenParserUtility;

        this.setPasswordEncoder(passwordEncoder);
    }

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

        String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
                : authentication.getName();

        boolean cacheWasUsed = true;
        UserDetails user = this.getUserCache().getUserFromCache(username);

        if (user == null) {
            cacheWasUsed = false;

            try {
                ResponseEntity response = employeeService.login(new AuthenticationRequestDto(String.valueOf(authentication.getPrincipal()), String.valueOf(authentication.getCredentials())));

                List<String> authHeaders = response.getHeaders().get(userJwtConfig.getHeader());
                Assert.notEmpty(authHeaders, "Header should be returned with authentication data.");
                Claims claims = tokenParserUtility.parse(authHeaders.get(0).replace(userJwtConfig.getPrefix(), "").replace(" ", ""));

                List<String> roles = (List<String>) claims.get(userJwtConfig.getAuthoritiesTag());
                user = new AccountAuthentication(
                        String.valueOf(claims.get(userJwtConfig.getLoginClaimTag())),
                        new String(Base64.getDecoder().decode(String.valueOf(claims.get(userJwtConfig.getPasswordClaimTag())).getBytes())),
                        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                        authHeaders.get(0),
                        Long.parseLong(String.valueOf(claims.get(userJwtConfig.getUidClaimTag()))));

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
