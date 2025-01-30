package com.scm.factory;

import com.scm.dto.ContactDTO;
import com.scm.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContactEmailSearch {

    @Autowired
    private ContactService contactService;

    public List<ContactDTO> searchByEmail(int userId, String email, String data) {
        return contactService.contactSearch(userId, email, data);
    }

}
