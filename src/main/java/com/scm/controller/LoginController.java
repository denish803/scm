package com.scm.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/login")
@Log4j2
public class LoginController {

    @GetMapping("/user")
    public ModelAndView login() {
        log.info("login controller start");
        return new ModelAndView("/login");
    }

}
