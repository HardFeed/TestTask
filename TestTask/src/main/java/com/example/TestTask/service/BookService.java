package com.example.TestTask.service;

import com.example.TestTask.model.Book;
import com.example.TestTask.model.BorrowedBook;
import com.example.TestTask.repository.BookRepository;
import com.example.TestTask.repository.BorrowedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    public Book createOrUpdateBook(Book book){
        return bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor())
                .map(existingBook -> {
                    existingBook.setAmount(existingBook.getAmount()+1);
                    return bookRepository.save(existingBook);
                })
                .orElseGet(() -> bookRepository.save(book));
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public void deleteBook(Long id){
        BorrowedBook borrowedBook = borrowedBookRepository.findByBookId(id).orElseThrow();
        if (borrowedBook == null){
            bookRepository.deleteById(id);
        }else {
            throw new RuntimeException("This book is borrowed");
        }

    }
}
