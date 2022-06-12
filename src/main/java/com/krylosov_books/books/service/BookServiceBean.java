package com.krylosov_books.books.service;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CompletionException;

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
        String checkResult = check(id);
        if(checkResult!=null){
            return checkResult;
        }
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
        String checkResult = check(id);
        if(checkResult!=null){
            return checkResult;
        }
        bookRepository.findById(id).map(entity->{
           entity.setDeleted(true);
           return bookRepository.save(entity);
        });
        return "The book has just been deleted!";
    }

    @Override
    public Object findBookByName(String name) {
        Book book;
        log.info("findBookByName() - start: name = {}", name);
        try{
            book = bookRepository.findByName(name);
            if(book.getDeleted()==null||!book.getDeleted()){
                log.info("findBookByName() - end: collection = {}", book);
                return book;
            }
        } catch (RuntimeException e){
            log.info("Exception: " + e);
            //throw new EntityNotFoundException(); - if we want a test that expects an EntityNotFoundException
            return "The book with the given name " + name + " does not exist!";
        }
        return "The book with the given name " + name + " has been deleted!";
    }

    @Override
    public Object findBookByAuthor(String author) {
        List <Book> list;
        ArrayList <Book> check = new ArrayList<>();
        log.info("findBookByAuthor() - start: author = {}", author);
        try{
            list = bookRepository.findByAuthor(author);
            if(list.isEmpty()){
                throw new NullPointerException();
            }
            for(Book index: list){
                if(index.getDeleted()==null||!index.getDeleted()){
                    check.add(index);
                }
            }
            log.info("findBookByAuthor() - end: list = {}", list);
            if(check.isEmpty()){
                return "The book(s) with the given author" + author + " has/have been deleted!";
            }
        } catch (RuntimeException e) {
            log.info("Exception: " + e);
            return "The book with the given author " + author + " does not exist!";
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
        return "The book with id " + id + " has not been found!";
    }

    private String check (int id){
        Book testBook;
        try{
            testBook = bookRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Book not found with id = " + id));}
        catch (EntityNotFoundException e) {
            e.printStackTrace();
            return "The book with id " + id + " has not been found";
        }
        if(testBook.getDeleted()!=null&&testBook.getDeleted()){
            return "The book with id " + id + " has been deleted!";
        }
        return null;
    }
}
