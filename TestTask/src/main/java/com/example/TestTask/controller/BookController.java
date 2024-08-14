package com.example.TestTask.controller;

import com.example.TestTask.model.Book;
import com.example.TestTask.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/createbook")
    public ResponseEntity<Book> createOrUpdateBook(@Valid @RequestBody Book book){
        return ResponseEntity.ok(bookService.createOrUpdateBook(book));
    }

    @GetMapping("/getbooks")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @DeleteMapping("/deletebook/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
