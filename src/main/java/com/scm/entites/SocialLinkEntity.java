package com.scm.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "social_link")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String linkDnLink;
    private String facebookLink;
    private int contactId;


}