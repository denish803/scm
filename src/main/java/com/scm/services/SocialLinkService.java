package com.scm.services;

import java.util.List;

public interface SocialLinkService {

    String addSocialLink(int contactId, List<String> socialLink);

    String updateSocialLink(int contactId, List<String> socialLink);

    boolean deleteSocialLink(int contactId);

}
