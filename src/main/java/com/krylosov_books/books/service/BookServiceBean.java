package com.krylosov_books.books.service;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.domain.Position;
import com.krylosov_books.books.repository.BookRepository;
import com.krylosov_books.books.repository.PositionRepository;
import com.krylosov_books.books.util.exception.ResourceAlreadyExistsException;
import com.krylosov_books.books.util.exception.ResourceNotFoundException;
import com.krylosov_books.books.util.exception.ResourceWasDeletedException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookServiceBean implements BookService {

    private final BookRepository bookRepository;

    private final PositionRepository positionRepository;

    @Override
    public Book create(Book book) {
        boolean exist = bookRepository.findAll()
                .stream()
                .anyMatch(book1 -> book1.getName().equals(book.getName()));
        if(exist){
            throw new ResourceAlreadyExistsException("The book with the given name already exists!");
        }
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll()
                .stream()
                .filter(book -> book.getDeleted() == null || !book.getDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public Book update(int id, Book book) {
        check(id);
        return bookRepository.findById(id).map(entity -> {
            entity.setName(book.getName());
            entity.setAuthor(book.getAuthor());
            entity.setPublicationYear(book.getPublicationYear());
            entity.setPagesNumber(book.getPagesNumber());
            entity.setPublisher(book.getPublisher());
            return bookRepository.save(entity);}).orElseThrow();
    }


    @Override
    public void removeBook(int id) {
        //check(id);
        bookRepository.findById(id).map(entity->{
           entity.setDeleted(true);
           return bookRepository.save(entity);
        });
    }

    @Override
    public Book findBookByName(String name) {
        Book book;
        log.info("findBookByName() - start: name = {}", name);
        try{
            book = bookRepository.findByName(name);
            if(book.getDeleted()==null||!book.getDeleted()){
                log.info("findBookByName() - end: book = {}", book);
                return book;
            } else throw new ResourceWasDeletedException();
        } catch (NullPointerException e){
            log.info("Exception: " + e);
            throw new ResourceNotFoundException("The book with name " + name + " has not been found!");
        }
    }

    @Override
    public List <Book> findBookByAuthor(String author) {
        log.info("findBookByAuthor() - start: author = {}", author);
        List <Book> list = bookRepository.findByAuthor(author)
                .stream()
                .filter(book -> book.getDeleted() == null || !book.getDeleted())
                .collect(Collectors.toList());
        if(list.isEmpty()){
            throw new ResourceNotFoundException("The book with author " + author +" has not been found!");
        }
        log.info("findBookByAuthor() - end: list = {}", list);
        return list;
    }

    @Override
    public void restore (int id){
        log.info("restore() - start: id = {}", id);
        int num = bookRepository.restoreById(id);
        if (num == 1){
            log.info("restore() - end: book restored");
        } else {
            log.info("restore() - end: book does not exist in the db");
            throw new ResourceNotFoundException("The book with id " + id + " has not been found!");
        }
    }

    @Override
    public Book getById(int id) {
        return returnBook(id);
    }

    @Override
    public Book addPosition(Integer id, Position position) {
        log.info("addPosition() - start: id = {}", id);
        check(id);
        log.info("addPosition() - end");
        return bookRepository.findById(id).map(entity->{
            entity.setPosition(position);
            return bookRepository.save(entity);
        }).orElseThrow();
    }

    @Override
    public Position getPositionByBookTitle(String name) {
        log.info("getPositionByBookTitle() - start: name = {}", name);
        var position = positionRepository.getPositionByBookName(name);
        log.info("getPositionByBookTitle() - end: position = {}", position);
        return position;
    }

    private void check (int id){
        if(returnBook(id).getDeleted() != null && returnBook(id).getDeleted()) {
            throw new ResourceWasDeletedException();
        }
    }

     private Book returnBook(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No books with id " + id + " have been found!"));
     }
}


