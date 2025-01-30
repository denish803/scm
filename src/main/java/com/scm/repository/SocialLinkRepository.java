package com.scm.repository;

import com.scm.entites.SocialLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialLinkRepository extends JpaRepository<SocialLinkEntity, Integer> {

    SocialLinkEntity findByContactId(int contactId);

}
