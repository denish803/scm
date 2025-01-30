package com.scm.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialLinkDTO {

    private int id;
    private String linkDnLink;
    private String facebookLink;
    private int contactId;
}
