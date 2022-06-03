package com.krylosov_books.books.service;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceBean implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
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
        Book testBook;
        try{
            testBook = bookRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Book not found with id = " + id));}
        catch (EntityNotFoundException e) {
            return "The book with id " + id + " has not been found";
        }
        if(testBook.getDeleted()!=null&&book.getDeleted()){
            return "The book with id " + id + " has been deleted!";
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
        Book testBook;
        try{
            testBook = bookRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Book not found with id = " + id));}
        catch (EntityNotFoundException e) {
            return "The book with id " + id + " has not been found";
        }
        if(testBook.getDeleted()!=null&&testBook.getDeleted()){
            return "The book with id " + id + " has been deleted!";
        }
        bookRepository.findById(id).map(entity->{
           entity.setDeleted(true);
           return bookRepository.save(entity);
        });
        return "The book has just been deleted!";
    }

    /**
     *
     *
     * Common delete method (deletes the book beyond retrieve)
     *
     */
    /*@Override
    public void removeBook(Book book) {
        bookRepository.delete(book);
    }*/
}
