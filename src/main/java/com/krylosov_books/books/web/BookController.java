package com.krylosov_books.books.web;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.dto.BookDeleteDto;
import com.krylosov_books.books.dto.BookDto;
import com.krylosov_books.books.dto.BookRestoreDto;
import com.krylosov_books.books.dto.PositionDto;
import com.krylosov_books.books.service.BookService;
import com.krylosov_books.books.util.config.BookConverter;
import com.krylosov_books.books.util.config.PositionConverter;
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
public class BookController implements BookSwagger{
    
    private final BookService bookService;
    private final BookConverter converter;
    private final PositionConverter positionConverter;

    @PostMapping("/books/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public BookDto createBook(@Valid @RequestBody BookDto requestForSave){
        var book = converter.getMapperFacade().map(requestForSave, Book.class);
        return converter.toDto(bookService.create(book));
    }

    @GetMapping("/books/all")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List <Book> getAllBooks() {
        return bookService.getBooks();
    }

    @PutMapping ("/books/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Book refreshBook(@PathVariable("id") Integer id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @PatchMapping ("/books/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BookDeleteDto removeBookById(@PathVariable Integer id) {
        bookService.removeBook(id);
        return converter.deleteDto(bookService.getById(id));
    }

    @GetMapping(value = "/books/byTitle", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BookDto findByTitle(String name){
        return converter.toDto(bookService.findBookByName(name));
    }

    @PatchMapping(value = "/books/restore", params = {"id"})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BookRestoreDto restoreBook(Integer id) {
        bookService.restore(id);
        return converter.restoreDto(bookService.getById(id));
    }

    @GetMapping(value = "/books/byAuthor", params = {"author"})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<Book> findAnyByAuthor(String author){
        return bookService.findBookByAuthor(author);
    }

    @PutMapping(value = "/books/{id}/addPosition")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BookDto addBookPosition(@PathVariable Integer id, @RequestBody PositionDto positionDto) {
        var position = positionConverter.fromDto(positionDto);
        var book = bookService.addPosition(id, position);
        return converter.toDto(book);
    }

    @GetMapping(value = "/books/position", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public PositionDto getPositionByTitleOfBook(String name) {
        var position = bookService.getPositionByBookTitle(name);
        return positionConverter.toDto(position);
    }
}
