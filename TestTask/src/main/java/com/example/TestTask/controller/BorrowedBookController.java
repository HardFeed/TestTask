package com.example.TestTask.controller;

import com.example.TestTask.model.Book;
import com.example.TestTask.service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/borrow")
public class BorrowedBookController {
    @Autowired
    private BorrowedBookService borrowedBookService;

    @PostMapping("/book/{bookId}/{memberId}")
    public ResponseEntity<Void> borrowBook(@PathVariable Long bookId, @PathVariable Long memberId){
        borrowedBookService.borrowBook(bookId, memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/return/{bookId}/{memberId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long bookId, @PathVariable Long memberId){
        borrowedBookService.returnBook(bookId, memberId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getall")
    public ResponseEntity<List> getAllBorrowedBooks(){
        return ResponseEntity.ok(borrowedBookService.getAllBorrowedBooks());
    }
    @GetMapping("/getamount")
    public ResponseEntity<HashMap> getAllAndAmount(){
        return ResponseEntity.ok(borrowedBookService.getAllBooksAndAmount());
    }


}
