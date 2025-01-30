package com.scm.controller;

import com.scm.dto.ContactDTO;
import com.scm.dto.MessageDTO;
import com.scm.enums.MessageTypeEnum;
import com.scm.factory.ContactSearchFactory;
import com.scm.helpers.LoggedInUserDataHelper;
import com.scm.services.ContactService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/user/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactSearchFactory contactSearchFactory;

    @GetMapping()
    public ModelAndView contactView(Authentication authentication) {
        log.info("contact view controller start");
        String email = LoggedInUserDataHelper.UserLoggedIn(authentication);
        return new ModelAndView("users/contact_list")
                .addObject("contactList", contactService.contactList(email));
    }

    @GetMapping("/add")
    public ModelAndView addContact(Model model) {
        log.info("contact add controller start");
        ContactDTO contactDTO = new ContactDTO();

        model.addAttribute("contactData", contactDTO);

        return new ModelAndView("/users/add_contact");
    }

    @PostMapping("/add")
    public  ModelAndView addContact(@Valid @ModelAttribute("contactData") ContactDTO contactDTO,  BindingResult bindingResult, HttpSession session) throws Exception {
        log.info("contact add post controller start");
//        get the validate error show
        if (bindingResult.hasErrors()) {
            log.error("Binding Result : {}", bindingResult.getAllErrors().toString());
            // error message show
            MessageDTO message = MessageDTO.builder().message("Invalid data").messageType(MessageTypeEnum.red).build();
            session.setAttribute("msg", message);
            return new ModelAndView("/users/add_contact");
        }
        boolean isInsertData = contactService.addContact(contactDTO);
        if (isInsertData) {
            return new ModelAndView("redirect:/user/contact");
        }
        return new ModelAndView("redirect:/user/contact/add?msg=INVALID_DATA");
    }

    @GetMapping("/update/id={id}")
    public ModelAndView updateContact(@PathVariable int id) {
        log.info("contact update get controller start");
        ContactDTO contact = contactService.getContact(id);
        return new ModelAndView("/users/update_contact")
                .addObject("contactData", contactService.getContact(id));
    }

    @PostMapping("/update")
    public ModelAndView updateContact(@Valid @ModelAttribute(name = "contactData") ContactDTO contactDTO, BindingResult bindingResult, HttpSession session) {
        log.info("contact update post controller start");
        if (bindingResult.hasErrors()) {
            log.error("bindingResult error : {}", bindingResult.getAllErrors().toString());
            return new ModelAndView("/users/update_contact");
        }
        String contactUpdateData = contactService.updateContact(contactDTO);
        if (contactUpdateData.equals("PHONE_NO_DUPLICATE")) {
            return new ModelAndView("redirect:/user/contact/update/id="+contactDTO.getId());
        }
        return new ModelAndView("redirect:/user/contact");
    }

    @GetMapping("/delete/id={id}")
    public ModelAndView deleteContact(@PathVariable int id) {
        log.info("contact delete controller start");
        contactService.deleteContact(id);
        return new ModelAndView("redirect:/user/contact?msg=CONTACT_DELETE");
    }

    @GetMapping("/search")
    public ModelAndView searchContact(@RequestParam String search, @RequestParam String searchData, HttpSession session) {
        log.info("contact search controller start");
        List<ContactDTO> userSearchContactData;
        if (searchData.isEmpty()) {
            userSearchContactData = new ArrayList<>();
        } else {
            userSearchContactData = contactSearchFactory.search((Integer) session.getAttribute("userID"), search, searchData);
        }
        return new ModelAndView("users/contact_search")
                .addObject("contactList", userSearchContactData);
    }
}