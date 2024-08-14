package com.example.TestTask.repository;

import com.example.TestTask.model.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    Optional<BorrowedBook> findByBookIdAndMemberId(Long bookId, Long memberId);
    Optional<BorrowedBook> findByBookId(Long bookId);
}
