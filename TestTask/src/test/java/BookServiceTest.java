package com.example.TestTask.service;

import com.example.TestTask.model.Book;
import com.example.TestTask.model.BorrowedBook;
import com.example.TestTask.repository.BookRepository;
import com.example.TestTask.repository.BorrowedBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowedBookRepository borrowedBookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrUpdateBook_NewBook() {
        Book book = new Book();
        book.setTitle("Book Title");
        book.setAuthor("Book Author");
        book.setAmount(1);

        when(bookRepository.findByTitleAndAuthor(any(String.class), any(String.class)))
                .thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class)))
                .thenReturn(book);

        Book result = bookService.createOrUpdateBook(book);

        assertNotNull(result);
        assertEquals("Book Title", result.getTitle());
        assertEquals("Book Author", result.getAuthor());
        assertEquals(1, result.getAmount());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testCreateOrUpdateBook_ExistingBook() {
        Book existingBook = new Book();
        existingBook.setTitle("Book Title");
        existingBook.setAuthor("Book Author");
        existingBook.setAmount(1);

        Book bookToUpdate = new Book();
        bookToUpdate.setTitle("Book Title");
        bookToUpdate.setAuthor("Book Author");
        bookToUpdate.setAmount(1);

        when(bookRepository.findByTitleAndAuthor(any(String.class), any(String.class)))
                .thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class)))
                .thenReturn(bookToUpdate);

        Book result = bookService.createOrUpdateBook(bookToUpdate);

        assertNotNull(result);
        assertEquals(2, result.getAmount());
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        assertEquals(2, bookService.getAllBooks().size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testDeleteBook_NotBorrowed() {
        Long bookId = 1L;

        when(borrowedBookRepository.findByBookId(anyLong())).thenReturn(Optional.empty());
        doNothing().when(bookRepository).deleteById(anyLong());

        bookService.deleteBook(bookId);

        verify(borrowedBookRepository, times(1)).findByBookId(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void testDeleteBook_Borrowed() {
        Long bookId = 1L;

        BorrowedBook borrowedBook = new BorrowedBook();
        when(borrowedBookRepository.findByBookId(anyLong())).thenReturn(Optional.of(borrowedBook));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            bookService.deleteBook(bookId);
        });

        assertEquals("This book is borrowed", thrown.getMessage());
        verify(borrowedBookRepository, times(1)).findByBookId(bookId);
        verify(bookRepository, never()).deleteById(bookId);
    }
}
