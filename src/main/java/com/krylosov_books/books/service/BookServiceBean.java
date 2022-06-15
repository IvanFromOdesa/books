package com.krylosov_books.books.service;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.repository.BookRepository;
import com.krylosov_books.books.util.ResourceNotFoundException;
import com.krylosov_books.books.util.ResourceWasDeletedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BookServiceBean implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Object create(Book book) {
        Book check;
        try{
            check = bookRepository.save(book);
        } catch (RuntimeException e){
            log.info("Exception: " + e);
            return "There is already a book with this name!";
        }
        return check;
    }

    @Override
    public List<Book> getBooks() {
        List <Book> test = bookRepository.findAll();
        ArrayList<Book> books = new ArrayList<>();
        for(Book index: test){
            if(index.getDeleted()==null||!index.getDeleted()){
                books.add(index);
            }
        }
        return books;
    }

    @Override
    public Object update(int id, Book book) {
        check(id);
        return bookRepository.findById(id).map(entity -> {
            entity.setName(book.getName());
            entity.setAuthor(book.getAuthor());
            entity.setPublicationYear(book.getPublicationYear());
            entity.setPagesNumber(book.getPagesNumber());
            entity.setPublisher(book.getPublisher());
            return bookRepository.save(entity);});
    }


    @Override
    public String removeBook(int id) {
        check(id);
        bookRepository.findById(id).map(entity->{
           entity.setDeleted(true);
           return bookRepository.save(entity);
        });
        return "The book has just been deleted!";
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
    public List<Book> findBookByAuthor(String author) {
        List <Book> list;
        ArrayList <Book> check = new ArrayList<>();
        log.info("findBookByAuthor() - start: author = {}", author);
        list = bookRepository.findByAuthor(author);
        if(list.isEmpty()){
            throw new ResourceNotFoundException("The book with author " + author +" has not been found!");
        }
        for(Book index: list){
            if(index.getDeleted()==null||!index.getDeleted()){
                check.add(index);
            }
        }
        log.info("findBookByAuthor() - end: list = {}", list);
        if(check.isEmpty()){
            throw new ResourceWasDeletedException();
        }
        return check;
    }

    /* Another variant of method restore
    @Override
    public Object restore(int id) {
        String checkResult = check(id);
        if(checkResult==null){
            return "The book with id " + id + " has not been previously deleted!";
        }
        if(checkResult.endsWith("found")){
            return checkResult;
        }
        return bookRepository.findById(id).map(entity->{
            entity.setDeleted(false);
            return bookRepository.save(entity);
        });
    }*/

    public String restore (int id){
        log.info("restore() - start: id = {}", id);
        int num = bookRepository.restoreById(id);
        if (num==1){
            log.info("restore() - end: book restored");
            return "The book is restored!";
        }
        log.info("restore() - end: book does not exist in the db");
        throw new ResourceNotFoundException("The book with id " + id + " has not been found!");
    }

    private String check (int id){
        Book testBook;
            testBook = bookRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No books with id " + id + " have been found!"));
        if(testBook.getDeleted()!=null&&testBook.getDeleted()){
            throw new ResourceWasDeletedException();
        }
        return null;
    }
}
