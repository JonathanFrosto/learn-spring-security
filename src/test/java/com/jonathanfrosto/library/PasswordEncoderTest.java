package com.jonathanfrosto.library;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {

    @Test
    void bcryptJonathan() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

        String jonathanPassword = passwordEncoder.encode("senhaTest1");
        String jonathanPassword2 = passwordEncoder.encode("senhaTest1");

        System.out.println(jonathanPassword);
        System.out.println(jonathanPassword2);

        assertThat(passwordEncoder.matches("senhaTest1", jonathanPassword)).isTrue();
        assertThat(passwordEncoder.matches("senhaTest1", jonathanPassword2)).isTrue();
    }
}
