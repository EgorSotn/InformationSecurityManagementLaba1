package org.example.laba.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http, org.springframework.security.web.authentication.AuthenticationFailureHandler failureHandler) throws Exception {
        http
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers("/", "/home", "/registered", "/login/error*").permitAll()
                                .requestMatchers(  "/user/edit/delete/*", "/user/add/**", "/user", "/user/edit/*").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(  "/user/edit").authenticated()
                                .anyRequest().authenticated()
                ).formLogin((form) -> form
                        .usernameParameter("custom_email")
                        .passwordParameter("custom_password").loginPage("/login")
                        .defaultSuccessUrl("/user/edit")
                        .failureHandler(failureHandler)
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        return http.csrf(AbstractHttpConfigurer::disable).build();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}



