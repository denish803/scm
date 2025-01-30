package com.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class DashboardController {

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        return new ModelAndView("/users/dashboard");
    }

}
    