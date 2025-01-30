package com.scm.api_controller;


import com.scm.dto.ContactDTO;
import com.scm.dto.ResponseDTO;
import com.scm.services.ContactService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/contact")
public class ContactAPIController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/profile")
    public ResponseDTO contactProfileAPI(@RequestParam(name = "id") int contactId) {
        log.info("contact profile API start");
        ContactDTO contact = contactService.getContact(contactId);
        log.info("Send The Json successfully {}", contact);
        return new ResponseDTO(false, "success", contact);
    }

}
