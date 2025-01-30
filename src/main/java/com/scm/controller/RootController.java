package com.scm.controller;

import com.scm.dto.UserDTO;
import com.scm.helpers.LoggedInUserDataHelper;
import com.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private UserService userService;

//    this is work for every request using @ModelAttribute
    @ModelAttribute
    public void LogInUserInfo(Authentication authentication, Model model, HttpSession session) {

        log.info("Login user from root controller");

//        if not login user
        if (authentication == null) {
            return;
        }

        String email = LoggedInUserDataHelper.UserLoggedIn(authentication);
        UserDTO userData = userService.getUserEmail(email);
        session.setAttribute("userID", userData.getId());
        model.addAttribute("loginUser", userData);

    }

}
