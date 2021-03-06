package com.jonathanfrosto.library.controllers;

import com.jonathanfrosto.library.domain.entities.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @GetMapping
    public List<Book> getBooks() {
        return Arrays.asList(new Book("123", "Description"));
    }
}
