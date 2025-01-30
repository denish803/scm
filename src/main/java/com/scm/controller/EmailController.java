package com.scm.controller;

import com.scm.dto.MessageDTO;
import com.scm.enums.MessageTypeEnum;
import com.scm.services.EmailService;
import com.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
@Controller
@RequestMapping("/auth/email")
public class EmailController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ModelAndView emailSender() {
        log.info("Email sender controller start");
        return new ModelAndView("/users/email_sender");
    }

    @PostMapping
    public ModelAndView emailSender(String to, String subject, String message, HttpSession session) {
        log.info("Email sender post controller start");
        String sendEmail = emailService.sendEmail(to, subject, message);
        if (sendEmail.equals("NOT_SEND_MAIL")) {
            session.setAttribute("msg",
                    MessageDTO
                            .builder()
                            .message("Mail not send try second time")
                            .messageType(MessageTypeEnum.red)
                            .build()
            );
        }
        return new ModelAndView("redirect:/auth/email");
    }

    @RequestMapping("/verify")
    public ModelAndView verifyEmail(@RequestParam("token") String token, HttpSession session) {
        log.info("email verify controller start");
        String userEmailToken = userService.getUserEmailToken(token);
        if (userEmailToken.equals("VALID_TOKEN")) {
            session.setAttribute("msg",
                    MessageDTO
                            .builder()
                            .message("Email id is valid you can login")
                            .messageType(MessageTypeEnum.green)
                            .build()
            );
            return new ModelAndView("/users/success_data");
        }

        session.setAttribute("msg",
                MessageDTO
                        .builder()
                        .message("Email id is not enable check the email id")
                        .messageType(MessageTypeEnum.red)
                        .build()
        );
        return new ModelAndView("/users/error_page");
    }

}
