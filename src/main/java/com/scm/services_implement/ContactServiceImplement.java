package com.scm.services_implement;

import com.scm.dto.ContactDTO;
import com.scm.entites.ContactEntity;
import com.scm.entites.UserEntity;
import com.scm.repository.ContactRepository;
import com.scm.repository.SocialLinkRepository;
import com.scm.repository.UserRepository;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.SocialLinkService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
 
import java.util.*;

@Service
@Log4j2
public class ContactServiceImplement implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SocialLinkService socialLinkService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ContactDTO> contactList() {

        List<ContactEntity> contactList = contactRepository.findAll();

        return contactList.stream().map(contactData -> modelMapper.map(contactList, ContactDTO.class)).toList();
    }

    @Override
    public List<ContactDTO> contactList(String email) {

        Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        String contactDataSQL = "SELECT * FROM contact c " +
                "LEFT JOIN social_link sl ON sl.contact_id = c.id " +
                "WHERE c.user_id = :userId " +
                "ORDER BY c.name ASC, c.phone_no ASC";

        Map<String, Object> contactDataSQLParam = new HashMap<>();
        contactDataSQLParam.put("userId", userEntity.get().getId());
        // list of user contact data
        List<Map<String, Object>> contactData = namedParameterJdbcTemplate.queryForList(contactDataSQL, contactDataSQLParam);

        List<ContactDTO> contacts = new ArrayList<>();

         for (Map<String, Object> c: contactData) {

             List<String> links = new ArrayList<>();
             links.add((String) c.get("link_dn_link"));
             links.add((String)c.get("facebook_link"));

            contacts.add(
                    ContactDTO.builder()
                            .id((Integer) c.get("id"))
                            .name((String)c.get("name"))
                            .email((String)c.get("email"))
                            .phoneNo((String)c.get("phone_no"))
                            .address((String)c.get("address"))
                            .image((String) c.get("image"))
                            .cloudinaryImagePublicId((String) c.get("cloudinary_image_public_id"))
                            .description((String) c.get("description"))
                            .favorite((Boolean) c.get("favorite"))
                            .socialLink(links)
                            .userId((Integer) c.get("user_id"))
                            .build()
            );
        }

         log.info("contact list send successful at contactList");

        return contacts;
    }

    @Override
    public ContactDTO getContact(int id) {

        String contactDataSQL = "SELECT * FROM contact c LEFT JOIN social_link sl ON sl.contact_id = c.id WHERE c.id = :Id";
        Map<String, Object> contactDataSQLParam = new HashMap<>();
        contactDataSQLParam.put("Id", id);

        // get contact data
        Map<String, Object> contactData = namedParameterJdbcTemplate.queryForMap(contactDataSQL, contactDataSQLParam);

            List<String> links = new ArrayList<>();
            links.add((String) contactData.get("link_dn_link"));
            links.add((String)contactData.get("facebook_link"));

        ContactDTO contact = ContactDTO.builder()
                .id((Integer) contactData.get("id"))
                .name((String)contactData.get("name"))
                .email((String)contactData.get("email"))
                .phoneNo((String)contactData.get("phone_no"))
                .address((String)contactData.get("address"))
                .image((String) contactData.get("image"))
                .cloudinaryImagePublicId((String) contactData.get("cloudinary_image_public_id"))
                .description((String) contactData.get("description"))
                .favorite((Boolean) contactData.get("favorite"))
                .socialLink(links)
                .userId((Integer) contactData.get("user_id"))
                .build();

        log.info("Contact send successful at get contact function : {}", contact.toString());

        return contact;

    }

    @Override
    public boolean addContact(ContactDTO contactDTO) {
        try {
            Optional<ContactEntity> addContact = contactRepository.findByPhoneNo(contactDTO.getPhoneNo());

            if (addContact.isPresent()) {
                return false;
            }

                // set the public id
                String fileName = UUID.randomUUID().toString();
                contactDTO.setCloudinaryImagePublicId(fileName);

                // set the image link
                contactDTO.setImage(imageService.uploadImage(contactDTO.getMultipartFileImage(), contactDTO.getCloudinaryImagePublicId()));

                if (contactDTO.getImage() == null) {
                    contactDTO.setCloudinaryImagePublicId(null);
                }

                ContactEntity save = contactRepository.save(modelMapper.map(contactDTO, ContactEntity.class));

                socialLinkService.addSocialLink(save.getId(), contactDTO.getSocialLink());

                log.info("contact Insert Successfully at addContact : {}", save.toString());
                return true;
        } catch (Exception e) {
            log.error("Contact not Add : ", e);
            return false;
        }
    }

    @Override
    public String updateContact(ContactDTO contactDTO) {

        String contactFindNumberSQL = "SELECT COUNT(*) count FROM contact c " +
                "WHERE c.phone_no = :phoneNo AND c.id != :contactId";

        Map<String, Object> contactFindNumberSQLParam = new HashMap<>();
        contactFindNumberSQLParam.put("phoneNo", contactDTO.getPhoneNo());
        contactFindNumberSQLParam.put("contactId", contactDTO.getId());

        // get number is duplicate.
        Map<String, Object> maps = namedParameterJdbcTemplate.queryForMap(contactFindNumberSQL, contactFindNumberSQLParam);

        if ((Long)maps.get("count") == 1) {
            log.info("Phone number is duplicate");
            return "PHONE_NO_DUPLICATE";
        }

        Optional<ContactEntity> contactEntityData = contactRepository.findById(contactDTO.getId());
        if (contactEntityData.isPresent()) {
            ContactEntity contactEntity = contactEntityData.get();
            try {
                String socialLinksInfo = socialLinkService.updateSocialLink(contactDTO.getId(), contactDTO.getSocialLink());
                if (socialLinksInfo.equals("NOT_UPDATE_LINK")) {
                    log.error("Not Update Social Links.");
                    return "NOT_UPDATE_SOCIAL_LINK";
                }

                // set image URL
                if (contactDTO.getMultipartFileImage().isEmpty()) {
                    contactDTO.setImage(contactEntity.getImage());
                    contactDTO.setCloudinaryImagePublicId(contactDTO.getCloudinaryImagePublicId());
                } else {
                    String imageName = UUID.randomUUID().toString();
                    String imageURL = imageService.uploadImage(contactDTO.getMultipartFileImage(), imageName);
                    contactDTO.setImage(imageURL);
                    contactDTO.setCloudinaryImagePublicId(imageName);
                }

                modelMapper.map(contactDTO, contactEntity);
                contactRepository.save(contactEntity);

                log.info("update contact successfully : {}", contactEntity.toString());
                return "CONTACT_UPDATE";
            } catch (Exception e) {
                log.error("Contact not Update : ", e);
                return "CONTACT_NOT_UPDATE";
            }
        }
        log.error("contact Data not Found : {} ", contactDTO.toString());
        return "DATA_NOT_FOUND";
    }

    @Override
    public boolean deleteContact(int id) {

        socialLinkService.deleteSocialLink(id);

        contactRepository.deleteById(id);
        log.info("contact Delete successfully : {}", id);
        return true;

    }

    @Override
    public List<ContactDTO> contactSearch(int userID, String searchValue, String searchData) {
        String searchSQL = "SELECT * FROM contact c " +
                "INNER JOIN social_link sl ON c.id = sl.contact_id " +
                "WHERE c.user_id = "+userID+" AND c."+ searchValue+ " LIKE '%"+searchData +"%'";

        List<Map<String, Object>> contactDataList = jdbcTemplate.queryForList(searchSQL);

        List<ContactDTO> contactDTO = new ArrayList<>();

        for (Map<String, Object> contact : contactDataList) {

            List<String> links = new ArrayList<>();
            links.add((String) contact.get("link_dn_link"));
            links.add((String)contact.get("facebook_link"));

            contactDTO.add(
                    ContactDTO
                            .builder()
                            .id((Integer) contact.get("id"))
                            .name((String) contact.get("name"))
                            .email((String) contact.get("email"))
                            .phoneNo((String) contact.get("phone_no"))
                            .address((String)contact.get("address"))
                            .image((String) contact.get("image"))
                            .cloudinaryImagePublicId((String) contact.get("cloudinary_image_public_id"))
                            .description((String) contact.get("description"))
                            .favorite((Boolean) contact.get("favorite"))
                            .socialLink(links)
                            .userId((Integer) contact.get("user_id"))
                            .build()
            );
        }

        log.info("Search contact Data  : {}", contactDTO.toArray());

        return contactDTO;
    }

}
