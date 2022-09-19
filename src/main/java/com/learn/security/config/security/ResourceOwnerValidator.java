package com.learn.security.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ResourceOwnerValidator {

    public boolean validate(String email, Authentication authentication) {
        String principal = (String) authentication.getPrincipal();
        return email != null && email.equals(principal);
    }

}
