package com.scm.services;

import java.util.List;

import com.scm.dto.UserDTO;

public interface UserService {
    boolean userInsert(UserDTO userDTO);

    boolean userUpdate(UserDTO userDTO);

    boolean userDelete(int id);

    UserDTO userGet(int id);

    List<UserDTO> userGetList();

    UserDTO getUserEmail(String email);

    String getUserEmailToken(String token);
}
