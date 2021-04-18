package ru.itis.javalab.trello.web.security.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.itis.javalab.trello.api.exception.NotFoundException;
import ru.itis.javalab.trello.web.security.provider.JwtAuthenticationProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public JwtFilter(JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String authorization = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String jwt = authorization.substring(7);

            try {
                jwtAuthenticationProvider.authenticate(jwt).ifPresent(authentication -> {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
            } catch (NotFoundException e) {
                throw new IllegalStateException("NotFoundException");
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
