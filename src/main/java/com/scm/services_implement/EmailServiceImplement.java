package com.scm.services_implement;

import com.scm.helpers.LinkGeneratorHelper;
import com.scm.services.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class EmailServiceImplement implements EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private LinkGeneratorHelper linkGeneratorHelper;

    @Override
    public String sendEmail(String to, String subject, String body) {
        try {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setText(body);
            simpleMailMessage.setSubject(subject);

            javaMailSender.send(simpleMailMessage);
            log.info("Mail send successfully.");
            return "SEND_MAIL";
        } catch (Exception exception) {
            log.error("Email not sent successfully : ", exception);
            return "NOT_SEND_MAIL";
        }
    }

    @Override
    public boolean LoginEmailSend(String to, String emailToken) {
        try {
            log.info("Login Email Send");
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(from);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setText("click to link and verify the email \n \t"
                    + linkGeneratorHelper.emailLinkGenerator(emailToken));
            simpleMailMessage.setSubject("Verify Email on your Account");
            return true;
        } catch (Exception exception) {
            log.error("Email not sent successfully : ", exception);
            return false;
        }
    }
}
