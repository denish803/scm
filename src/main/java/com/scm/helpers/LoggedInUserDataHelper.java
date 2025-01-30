package com.scm.helpers;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;


@Log4j2
public class LoggedInUserDataHelper {

//    this is get the user Data which is use in login and return email
    public static String UserLoggedIn(Authentication authentication) {

//        use of get the information about login
        if (authentication.isAuthenticated()) {
            log.info("Get data from local database");
            return authentication.getName();
        }
        return "";
    }

}
