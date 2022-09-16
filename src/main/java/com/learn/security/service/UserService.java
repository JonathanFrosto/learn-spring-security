package com.learn.security.service;

import com.learn.security.service.dto.UserDTO;

import java.util.List;

public interface UserService {


    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO save(UserDTO userDTO);

    void turnAdmin(Long id);
}
