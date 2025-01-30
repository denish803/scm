package com.scm.dto;

import com.scm.enums.SingupProviderEnum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO  {

    private int id;

    // personal information
    @Size(min = 3, message = "Minimum Three Character is required*")
    private String name; 
    @NotBlank(message = "Email is required*")
    @Email
    private String email;
    @Size(min = 10, max = 12, message = "Number Invalid*")
    private String phone; 
    private String password; 
    @Size(min = 5, message = "Minimum five Character required*")
    private String about; 
    private String profilePic;

    private MultipartFile image = null;
    private String cloudinaryImagePublicId;

    private String emailToken;

    // verify information
    private boolean isEnabled = false;
    private boolean isEmailVerified = false;
    private boolean isPhoneVerified = false;

    //  singup Provider for user
    @Enumerated(value = EnumType.STRING)
    private SingupProviderEnum provider = SingupProviderEnum.SELF;
    private String providerId;

}
