package com.scm.services;

import com.scm.dto.ContactDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {

    List<ContactDTO> contactList();

    List<ContactDTO> contactList(String email);

    ContactDTO getContact(int id);

    boolean addContact(ContactDTO contactDTO);

    String updateContact(ContactDTO contactDTO);

    boolean deleteContact(int id);

    List<ContactDTO> contactSearch(int userID, String search, String searchData);
}
