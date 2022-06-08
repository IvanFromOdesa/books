package com.krylosov_books.books.repository;

import com.krylosov_books.books.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByName (String name);

    @Modifying
    @Transactional
    @Query ("UPDATE Book b SET b.isDeleted = false WHERE b.id=?1")
    int restoreById (int id);

    List<Book> findByAuthor(String author);
}
