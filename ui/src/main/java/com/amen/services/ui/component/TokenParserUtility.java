package com.amen.services.ui.component;

import com.amen.services.ui.config.UserJwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author amen
 * Date: 11/13/18
 * Time: 12:59 PM
 */
@Component
@RequiredArgsConstructor
public class TokenParserUtility {
    public static final String ISSUER_UUID = "AUTH_USER_ISSUER_ApsServices";
    private final UserJwtConfig userJwtConfig;

    public Claims parse(@NotNull String token) {
        return Jwts.parser()
                .setSigningKey(userJwtConfig.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
