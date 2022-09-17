package com.learn.security.controllers;

import com.learn.security.service.UserService;
import com.learn.security.service.dto.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PatchMapping("/{id}/admin")
    @PreAuthorize("hasAuthority('user.turn-admin')")
    public void turnAdmin(@PathVariable Long id) {
        userService.turnAdmin(id);
    }

    @PostMapping("/")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @PutMapping("/change-password")
    @PreAuthorize("@resourceOwnerValidator.validate(#userDTO.getEmail(), authentication)")
    public void changePassword(@RequestBody UserDTO userDTO) {
        userService.changePassword(userDTO);
    }
}
