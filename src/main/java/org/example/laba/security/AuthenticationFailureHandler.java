package org.example.laba.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.laba.exception.InvalidValidPassword;
import org.example.laba.exception.UserNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
public class AuthenticationFailureHandler {
    @Bean
    public org.springframework.security.web.authentication.AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                String message = null;
                if (exception.getCause() instanceof UserNotFoundException) {
                    message = "Пользователь с таким email не существует";
                } else if (exception.getCause() instanceof LockedException) {
                    message = "Учетка данного пользователя заблокирована";
                } else {
                    message = "Произошла ошибка при аутентификации";
                }
                String encode = URLEncoder.encode(message, StandardCharsets.UTF_8);
                response.sendRedirect("/login?message=" + encode);
            }
        };
    }
}
