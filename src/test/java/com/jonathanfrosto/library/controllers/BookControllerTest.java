package com.jonathanfrosto.library.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BookControllerTest {

    @Autowired
    BookController bookController;

    @Autowired
    MockMvc mockMvc;

    @Value("${spring.security.user.name}")
    String user;

    @Value("${spring.security.user.password}")
    String password;

    String basicEncoded;

    @BeforeEach
    void setup() {
        basicEncoded = Base64.getEncoder().encodeToString(user.concat(":").concat(password).getBytes());
    }

    @Test
    void getBooks() throws Exception {
        mockMvc.perform(get("/book")
                        .header("Authorization", "Basic " + basicEncoded))
                .andExpect(status().isOk());
    }

    
}