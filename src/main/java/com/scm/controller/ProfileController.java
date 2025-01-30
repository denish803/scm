package com.scm.controller;

import com.scm.dto.UserDTO;
import com.scm.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView profile(@AuthenticationPrincipal OAuth2User oAuth2User) {
        log.info("profile controller start");
        return new ModelAndView("/users/profile");
    }

    @GetMapping("/update")
    public ModelAndView update() {
        log.info("profile update get controller start");
        return new ModelAndView("/users/profile_update");
    }

    @PostMapping("/update")
        public ModelAndView update(UserDTO userDTO) {
        log.info("profile update post controller start");
        userService.userUpdate(userDTO);
        return new ModelAndView("redirect:/user/profile");
    }

    @GetMapping("/delete/id={id}")
    public ModelAndView delete(@PathVariable int id) {
        log.info("profile delete get controller start");
        userService.userDelete(id);
        return new ModelAndView("redirect:/logout?");
    }

}
