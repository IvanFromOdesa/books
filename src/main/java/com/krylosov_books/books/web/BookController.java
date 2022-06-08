package com.krylosov_books.books.web;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
    
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Object createBook(@RequestBody Book book){
        return bookService.create(book);
    }

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllUsers() {
        return bookService.getBooks();
    }

    @PutMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object refreshBook(@PathVariable("id") Integer id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping ("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String removeBookById(@PathVariable Integer id) {
        return bookService.removeBook(id);
    }

    @GetMapping(value = "/books", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public Object findByTitle(String name){
        return bookService.findBookByName(name);
    }

    @PatchMapping(value = "/books/restore", params = {"id"})
    @ResponseStatus(HttpStatus.OK)
    public String restoreBook(Integer id) {
        return bookService.restore(id);
    }

    @GetMapping(value = "/books", params = {"author"})
    @ResponseStatus(HttpStatus.OK)
    public Object findAnyByAuthor(String author){
        return bookService.findBookByAuthor(author);
    }
}
