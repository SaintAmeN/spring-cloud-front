package com.amen.services.ui.feign;

import com.amen.services.ui.component.SessionAuth;
import com.amen.services.ui.config.UserJwtConfig;
import com.amen.services.ui.model.exception.UserNotLoggedInException;
import com.amen.services.ui.model.AccountAuthentication;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author amen
 * @project user-ms
 * @created 17.07.19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FeignRequestInterceptor implements RequestInterceptor {
    private final SessionAuth sessionAuth;
    private final UserJwtConfig userJwtConfig;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (sessionAuth != null) {
            System.out.println(sessionAuth);
            try {
                AccountAuthentication accountAuthentication = (AccountAuthentication) sessionAuth.getAuthentication().getPrincipal();
                System.out.println(accountAuthentication);

                requestTemplate.header(userJwtConfig.getHeader(), combine(userJwtConfig.getPrefix(), accountAuthentication.getToken()));
            } catch (UserNotLoggedInException e) {
                log.warn("Not logged in.");
            }
        }
    }

    private String combine(String prefix, String token) {
        return prefix + token;
    }
}
