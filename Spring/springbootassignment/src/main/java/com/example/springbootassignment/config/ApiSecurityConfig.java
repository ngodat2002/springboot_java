package com.example.springbootassignment.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    final UserDetailsService userDetailsService;

    final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/api/v1/products",
                        "/api/v1/account/login",
                        "/api/v1/account/register")
                .permitAll();
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/v1/user**", "/api/v1/accounts/profile")
                .hasAnyAuthority("USER");
        http.authorizeRequests()
                .antMatchers("/api/v1/admin**")
                .hasAnyAuthority(
                        "ADMIN");
        http.addFilterBefore(new ApiAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
