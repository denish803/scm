package com.scm.configuration;

import com.scm.dto.MessageDTO;
import com.scm.enums.MessageTypeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Configuration
@Log4j2
public class AuthenticationFailureConfig implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.info("Authentication Failure Configuration");
//        use to if disable account
        if (exception instanceof DisabledException) {
            HttpSession session = request.getSession();
            session.setAttribute("msg",
                    MessageDTO
                            .builder()
                            .message("but Account is disable")
                            .messageType(MessageTypeEnum.red)
                            .build()
            );
            response.sendRedirect("/login/user?error=disable");
        } else {
            response.sendRedirect("/login/user?error=true");
        }
    }
}
