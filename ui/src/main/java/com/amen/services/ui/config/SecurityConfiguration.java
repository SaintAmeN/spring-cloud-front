package com.amen.services.ui.config;

import com.amen.services.ui.component.AccountAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountAuthenticationProvider accountAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/webjars/**", "/login", "/css/**", "/error/**", "/assets/**", "/dist/**", "/i18n/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN") /* Na potem, wszystko co w /admin/ musi mieć rolę admina*/
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/user/login")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(accountAuthenticationProvider);
    }
}
