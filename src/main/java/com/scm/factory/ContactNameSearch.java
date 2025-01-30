package com.scm.factory;

import com.scm.dto.ContactDTO;
import com.scm.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContactNameSearch {

    @Autowired
    private ContactService contactService;

    public List<ContactDTO> searchByName(int userId, String name, String data) {
        return contactService.contactSearch(userId, name, data);
    }

}
