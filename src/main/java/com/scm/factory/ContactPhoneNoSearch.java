package com.scm.factory;

import com.scm.dto.ContactDTO;
import com.scm.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContactPhoneNoSearch {

    @Autowired
    private ContactService contactService;

    public List<ContactDTO> searchByPhoneNo(int userId, String number, String data) {
        return contactService.contactSearch(userId, number, data);
    }

}
