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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BookControllerTest {

    @Autowired
    BookController bookController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getBooks() throws Exception {
        mockMvc.perform(get("/book")
                        .with(httpBasic("jonathan", "senhaTest1")))
                .andExpect(status().isOk());
    }

    @Test
    void getBooksSecondUser() throws Exception {
        mockMvc.perform(get("/book")
                        .with(httpBasic("convidado", "senhaTest2")))
                .andExpect(status().isOk());
    }

}