package com.scm.dto;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {

    private int id;
    @NotBlank(message = "Not allow Blank")
    private String name;
    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email")
    private String email;
    @NotBlank(message = "Not Blank number")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Mobile Number")
    private String phoneNo;
    private String address;
    private MultipartFile multipartFileImage;
    private String image;
    private String cloudinaryImagePublicId;
    private String description;
    private boolean favorite = false;
    @ElementCollection
    private List<String> socialLink;

    private int userId;

}
