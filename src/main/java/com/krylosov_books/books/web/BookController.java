package com.krylosov_books.books.web;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.dto.BookDeleteDto;
import com.krylosov_books.books.dto.BookDto;
import com.krylosov_books.books.dto.BookRestoreDto;
import com.krylosov_books.books.service.BookService;
import com.krylosov_books.books.util.config.BookConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class BookController {
    
    private final BookService bookService;

    private final BookConverter converter;

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody BookDto requestForSave){
        Book book = converter.getMapperFacade().map(requestForSave, Book.class);
        BookDto bookDto = converter.toDto((Book) bookService.create(book));
        return bookDto;
    }

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List <Book> getAllBooks() {
        return bookService.getBooks();
    }

    @PutMapping ("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book refreshBook(@PathVariable("id") Integer id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @PatchMapping ("/books/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BookDeleteDto removeBookById(@PathVariable Integer id) {
        bookService.removeBook(id);
        return converter.deleteDto(bookService.getById(id));
    }

    @GetMapping(value = "/books", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public BookDto findByTitle(String name){
        return converter.toDto(bookService.findBookByName(name));
    }

    @PatchMapping(value = "/books/restore", params = {"id"})
    @ResponseStatus(HttpStatus.OK)
    public BookRestoreDto restoreBook(Integer id) {
        bookService.restore(id);
        return converter.restoreDto(bookService.getById(id));
    }

    @GetMapping(value = "/books", params = {"author"})
    @ResponseStatus(HttpStatus.OK)
    public List<Book> findAnyByAuthor(String author){
        return bookService.findBookByAuthor(author);
    }
}
