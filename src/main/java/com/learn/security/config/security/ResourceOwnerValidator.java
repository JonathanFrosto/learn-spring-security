package com.learn.security.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class ResourceOwnerValidator {

    public boolean validate(String email, Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return email != null && email.equals(principal.getUsername());
    }

}
