package com.krylosov_books.books;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.repository.BookRepository;
import lombok.Builder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Builder
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryTests {

    @Autowired
    BookRepository bookRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveBookTest() {

        Book book = Book.builder().name("Super Book").author("JJ Redbrick").build();

        bookRepository.save(book);

        Assertions.assertThat(book.getId()).isGreaterThan(0);
    }
}
