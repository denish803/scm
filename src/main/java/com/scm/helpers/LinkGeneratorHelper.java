package com.scm.helpers;

public class LinkGeneratorHelper {

    public String emailLinkGenerator(String emailToken) {
        return "http://localhost:8080/auth/email/verify?token=" + emailToken;
    }

}
