package cn.perhome.snapha.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class InvalidAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.warn("Unauthorized => {}", request.getRequestURI());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Jwt authentication failed");
            return;
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Jwt authentication expired");
    }

}
