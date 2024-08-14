package com.example.TestTask.service;

import com.example.TestTask.model.Book;
import com.example.TestTask.model.BorrowedBook;
import com.example.TestTask.model.Member;
import com.example.TestTask.repository.BookRepository;
import com.example.TestTask.repository.BorrowedBookRepository;
import com.example.TestTask.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BorrowedBookService {
    @Autowired
    private BorrowedBookRepository borrowedBookRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;

    public void borrowBook(Long bookId, Long memberId){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        if (book.getAmount() > 0 && member.getAmount() < 10){
            book.setAmount(book.getAmount()-1);
            member.setAmount(member.getAmount()+1);
            member.setBorrowedBooks(Collections.singletonList(book.getTitle()));

            BorrowedBook borrowedBook = new BorrowedBook();
            borrowedBook.setBook(book);
            borrowedBook.setMember(member);

            borrowedBookRepository.save(borrowedBook);
        }
    }

    public void returnBook(Long bookId, Long memberId){
        BorrowedBook borrowedBook = borrowedBookRepository.findByBookIdAndMemberId(bookId, memberId)
                .orElseThrow();

        Book book = borrowedBook.getBook();
        book.setAmount(book.getAmount()+1);
        Member member = borrowedBook.getMember();
        member.setAmount(member.getAmount()-1);

        bookRepository.save(book);
        memberRepository.save(member);

        borrowedBookRepository.delete(borrowedBook);
    }

    public List<String> getAllBorrowedBooks(){
        List<String> names = new ArrayList<>();
        List<BorrowedBook> books = borrowedBookRepository.findAll();
        for (int i = 0; i < books.size(); i++){
            names.add(books.get(i).getBook().getTitle());
        }
        return names;
    }

    public HashMap<String, Integer> getAllBooksAndAmount(){
        HashMap<String, Integer> result = new HashMap<>();
        List<BorrowedBook> books = borrowedBookRepository.findAll();
        for (BorrowedBook borrowedBook : books){
            String title = borrowedBook.getBook().getTitle();
            result.put(title, result.getOrDefault(title, 0)+1);
        }
        return result;
    }
}
