package com.scm.configuration;

import com.scm.factory.ContactEmailSearch;
import com.scm.factory.ContactNameSearch;
import com.scm.factory.ContactPhoneNoSearch;
import com.scm.factory.ContactSearchFactory;
import com.scm.helpers.LinkGeneratorHelper;
import com.scm.helpers.LoggedInUserDataHelper;

import lombok.extern.log4j.Log4j2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class ClassBeenConfig {


    @Bean
    public LoggedInUserDataHelper loggedInUserHelper() {
        return new LoggedInUserDataHelper();
    }

    @Bean
    public ContactSearchFactory contactSearchFactory() {
        return new ContactSearchFactory();
    }

    @Bean
    public ContactNameSearch contactNameSearch() {
        return new ContactNameSearch();
    }

    @Bean
    public ContactPhoneNoSearch contactPhoneNoSearch() {
        return new ContactPhoneNoSearch();
    }

    @Bean
    public ContactEmailSearch contactEmailSearch() {
        return new ContactEmailSearch();
    }

    @Bean
    public LinkGeneratorHelper linkGeneratorHelper() {
        return new LinkGeneratorHelper();
    }
}

