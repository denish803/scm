package com.scm.services_implement;

import com.scm.entites.SocialLinkEntity;
import com.scm.repository.SocialLinkRepository;
import com.scm.services.SocialLinkService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class SocialLinkServiceImplement implements SocialLinkService {

    @Autowired
    private SocialLinkRepository socialLinkRepository;

    @Override
    public String addSocialLink(int contactId, List<String> socialLink) {
            try {
                socialLinkRepository.save(
                        SocialLinkEntity
                                .builder()
                                .linkDnLink(socialLink.get(1))
                                .facebookLink(socialLink.get(0))
                                .contactId(contactId)
                                .build()
                );
            } catch (Exception e) {
                log.error("Social Link not insert successfully : ", e);
                return "DATA_NOT_INSERTED";
            }
        log.info("Social Link Data Inserted successfully : {}", socialLink.toString());
        return "DATA_INSERTED";
    }

    @Override
    public String updateSocialLink(int contactId, List<String> socialLink) {

        try {
            SocialLinkEntity contactSocialLinkEntity = socialLinkRepository.findByContactId(contactId);

            contactSocialLinkEntity.setLinkDnLink(socialLink.get(0));
            contactSocialLinkEntity.setFacebookLink(socialLink.get(1));

            socialLinkRepository.save(contactSocialLinkEntity);
            log.info("Social link update successfully : {}", socialLink.toArray().toString());
            return "UPDATE_LINKS";
        } catch (Exception e) {
            log.error("Social Links not update : ", e);
            return "NOT_UPDATE_LINK";
        }
    }

    @Override
    public boolean deleteSocialLink(int contactId) {

        SocialLinkEntity socialLinkData = socialLinkRepository.findByContactId(contactId);

        try {
                socialLinkRepository.deleteById(socialLinkData.getId());
                log.info("social Link Delete successfully : {}", contactId);
                return true;
        } catch (Exception e) {
            log.error("Social Link not Delete : ", e);
            return false;
        }
    }
}
