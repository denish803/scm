package com.scm.helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class SessionHelper {

    public static void removeSession() {
        try {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
            .getSession();
            session.removeAttribute("msg");
            log.info("Remover Session successfully");
        } catch (Exception e) {
            log.error("Remove Session : ", e);
            e.printStackTrace();
        }
    }

}
