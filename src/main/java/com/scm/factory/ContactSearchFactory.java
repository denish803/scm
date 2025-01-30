package com.scm.factory;

import com.scm.dto.ContactDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Log4j2
public class ContactSearchFactory {

    @Autowired
    private ContactEmailSearch contactEmailSearch;

    @Autowired
    private ContactPhoneNoSearch contactPhoneNoSearch;

    @Autowired
    private  ContactNameSearch contactNameSearch;

    public List<ContactDTO> search(int userID, String searchValue, String searchData) {

        if (searchValue.equals("email")) {
            log.info("Email Factory Work");
            return contactEmailSearch.searchByEmail(userID, searchValue, searchData);
        } else if (searchValue.equals("phone_no")) {
            log.info("Phone number Factory Work");
            return contactPhoneNoSearch.searchByPhoneNo(userID,searchValue, searchData);
        } else if (searchValue.equals("name")) {
            log.info("name Factory Work");
            return contactNameSearch.searchByName(userID, searchValue, searchData);
        } else {
            log.error("Search Value s is Not Found: {}", searchValue);
        }
        return null;
    }

}
