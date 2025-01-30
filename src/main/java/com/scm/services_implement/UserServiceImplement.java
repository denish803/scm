package com.scm.services_implement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.scm.services.EmailService;
import com.scm.services.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.dto.UserDTO;
import com.scm.entites.UserEntity;
import com.scm.repository.UserRepository;
import com.scm.services.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserServiceImplement implements UserService {

     @Autowired
     private ModelMapper modelMapper;

     @Autowired
     private UserRepository userRepository;

     @Autowired
     private PasswordEncoder passwordEncoder;

     @Autowired
     private EmailService emailService;

     @Autowired
     private ImageService imageService;

    @Override
    public boolean userInsert(UserDTO userDTO) {
        try {
            UserEntity userEntity = modelMapper.map(userDTO, com.scm.entites.UserEntity.class);
            // password encode
             userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userEntity.setProviderId(userDTO.getProvider()+"_"+userDTO.getName().split(" ")[0]);

            // create email token
            String emailToken = UUID.randomUUID().toString();
            userEntity.setEmailToken(emailToken);

            // image name generate
            String fileName = UUID.randomUUID().toString();
            String imageURL = imageService.uploadImage(userDTO.getImage(), fileName);

            // profile image set on cloudinary
            userEntity.setProfilePic(imageURL);
            userEntity.setCloudinaryImagePublicId(fileName);

            userRepository.save(userEntity);
            boolean isMailSend = emailService.LoginEmailSend(userDTO.getEmail(), emailToken);

            if (!isMailSend) {
                log.error("Mail Not sent successfully");
            }

            log.info("user insert {}", userDTO);
            return true;
        } catch (Exception exception) {
            log.error("user Not insert", exception);
        }
        return false;
    }

    @Override
    public boolean userUpdate(UserDTO userDTO) {
        try {
            Optional<UserEntity> userData = userRepository.findById(userDTO.getId());
            if (userData.isPresent()) {
                UserEntity userEntity = userData.get();

                if (!userDTO.getImage().isEmpty()) {
                    String fileName = UUID.randomUUID().toString();
                    String imageURL = imageService.uploadImage(userDTO.getImage(), fileName);
                    userDTO.setProfilePic(imageURL);
                    userDTO.setCloudinaryImagePublicId(fileName);
                } else {
                    userDTO.setProfilePic(userEntity.getProfilePic());
                }

                userDTO.setEnabled(userEntity.isEnabled());
                userDTO.setPhoneVerified(userEntity.isPhoneVerified());
                userDTO.setEmailVerified(userEntity.isEmailVerified());
                userDTO.setPassword(userEntity.getPassword());

                modelMapper.map(userDTO, userEntity);
                userRepository.save(userEntity);

                log.info("User Update successfully : {}", userEntity);

                return true;
            } else {
                log.error("user Not Found : {}", userDTO.getId());
                return false;
            }
        } catch (Exception e) {
            log.error("user does not Update : ", e);
            return false;
        }
    }

    @Override
    public boolean userDelete(int id) {

        Optional<UserEntity> userDelete = userRepository.findById(id);

        if (userDelete.isPresent()) {
            userRepository.deleteById(id);
            log.info("user deleted successfully : {}", id);
            return true;
        }
        log.error("User id does not Found {} ", id);
        return false;
    }

    @Override
    public UserDTO userGet(int id) {
        Optional<UserEntity> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            return modelMapper.map(userData, UserDTO.class);
        }
        log.info("User Not Found : {}", id);
        return null;
    }

    @Override
    public List<UserDTO> userGetList() {

        List<UserEntity> userData = userRepository.findAll();

        return userData
                .stream()
                .map(e -> modelMapper.map(userData, UserDTO.class))
                .toList();

    }

    @Override
    public UserDTO getUserEmail(String email) {
        Optional<UserEntity> userEmail = userRepository.findByEmail(email);
        if (userEmail.isEmpty()) {
            log.error("user Email id Not found : {}", email);
            return  null;
        }
        return modelMapper.map(userEmail, UserDTO.class);
    }

    @Override
    public String getUserEmailToken(String token) {

        Optional<UserEntity> userEmail = userRepository.findByEmailToken(token);

        if (userEmail.isEmpty()) {
            log.error("user email token not found : {}", token);
            return "NOT_FOUND_TOKEN";
        }

        UserEntity userEntity = userEmail.get();
        userEntity.setEnabled(true);
        userEntity.setEmailVerified(true);
        userRepository.save(userEntity);
        log.info("User email token found successfully and update email enable: {}", token);
        return "VALID_TOKEN";

    }

}
