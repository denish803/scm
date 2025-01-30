package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scm.dto.MessageDTO;
import com.scm.dto.UserDTO;

import com.scm.entites.UserEntity;
import com.scm.enums.MessageTypeEnum;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/singup")
@Log4j2
public class SingupController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ModelAndView singup(Model model) {
        log.info("singup controller start");
        UserEntity userEntity = new UserEntity();
        model.addAttribute("userFormData", userEntity);
        return new ModelAndView("singup");
    }

    @PostMapping("/user")
    public ModelAndView singup(@Valid @ModelAttribute("userFormData") UserDTO userDTO,
                               BindingResult bindingResult, HttpSession httpSession) {
        log.info("singup post controller start");
        if (bindingResult.hasErrors()) {
            log.info("Errors : {}", bindingResult.getAllErrors());
            return new ModelAndView("singup");
        }

        boolean isUserInsert = userService.userInsert(userDTO);
        if (isUserInsert) {
            
            MessageDTO message = MessageDTO.builder().message(userDTO.getName()).messageType(MessageTypeEnum.green).build();

            httpSession.setAttribute("msg", message);
            return new ModelAndView("redirect:/login/user");
        } else {
            return new ModelAndView("redirect:/singup/user");
        }
    }

}

