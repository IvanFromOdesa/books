package com.krylosov_books.books;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookRepositoryTests {

    @Autowired
    BookRepository bookRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveBookTest() {
        Book book = Book.builder().name("Super Book").author("JJ Redbrick").build();

        bookRepository.save(book);
        assertThat(bookRepository.save(book)).isSameAs(book);

        Assertions.assertThat(book.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getBookTest() {
        Book book = bookRepository.findById(1).orElseThrow();

        Assertions.assertThat(book.getId()).isEqualTo(1);

    }

    @Test
    @Order(3)
    public void getListOfBookTest() {
        List<Book> booksList = bookRepository.findAll();

        Assertions.assertThat(booksList.size()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateBookTest() {
        Book book = bookRepository.findById(1).get();

        book.setName("Super Book");
        Book bookUpdated = bookRepository.save(book);

        Assertions.assertThat(bookUpdated.getName()).isEqualTo("Super Book");

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void findBookByNameTest() {
        Book book1 = bookRepository.findById(1).get();

        Book book2 = bookRepository.findByName("Super Book");

        Assertions.assertThat(book1.getName()).isEqualTo(book2.getName());
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void restoreByIdTest() {
        Optional <Book> book = bookRepository.findById(1).map(entity->{
            entity.setDeleted(true);
            return bookRepository.save(entity);
        });

        Book newBook = null;

        if(book.isPresent()){
            newBook = book.get();
        }

        Assertions.assertThat(newBook).isNotNull();
        Assertions.assertThat(newBook.getDeleted()).isEqualTo(true);
        Assertions.assertThat(bookRepository.restoreById(1)).isEqualTo(1);
    }

    @Test
    @Order(7)
    @Rollback(value = false)
    public void findByAuthorTest() {
        List <Book> book = bookRepository.findByAuthor("JJ Redbrick");
        Assertions.assertThat(book.get(0).getAuthor()).isEqualTo("JJ Redbrick");
    }
    @Test
    @Order(8)
    @Rollback(value = false)
    public void deleteBookTest() {
        Book book = bookRepository.findById(1).get();

        bookRepository.delete(book);

        Book newBook = null;

        Optional<Book> optionalBook = Optional.ofNullable(bookRepository.findByName("Super Book"));

        if (optionalBook.isPresent()) {
            newBook = optionalBook.get();
        }

        Assertions.assertThat(newBook).isNull();
    }
}
