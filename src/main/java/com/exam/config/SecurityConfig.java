package com.exam.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {


@Bean
public HttpSecurity httpSecurity(){
    return null;
}
    public void filterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/bumchik").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint((request, response, authException) ->
                                        response.sendError(HttpServletResponse.SC_ACCEPTED, "Accepted"))
                ).csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity, you might want to enable it in a production environment
                 .formLogin(formLogin-> formLogin.disable());
    }
}
