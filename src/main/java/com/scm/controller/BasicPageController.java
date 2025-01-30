package com.scm.controller;


import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
public class BasicPageController {


    @GetMapping("/home")
    public ModelAndView home(HttpSession session, Authentication authentication) {
        return new ModelAndView("home");
    }

    @GetMapping("/service")
    public ModelAndView service() {
        return new ModelAndView("service");
    }


    @GetMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("contact");
    }

    @GetMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about");
    }

}
