package com.amen.services.ui.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationInfo {
    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;
    private String token;
    private Long userId;


}
