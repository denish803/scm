package com.scm.services;


public interface EmailService {

    String sendEmail(String to, String subject, String body);

    boolean LoginEmailSend(String to, String emailToken);


}
