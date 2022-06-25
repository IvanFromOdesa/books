package com.krylosov_books.books.service;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.domain.Position;

import java.util.Collection;
import java.util.List;


public interface BookService {
    /**
     * Persists new book to database.
     *
     * @param book - book to persist.
     * @return - make book.
     */
    Book create (Book book);

    /**
     * Retrieves all present books (which are not deleted) to client.
     *
     *
     * @return - list of books.
     */

    List <Book> getBooks ();

    /**
     * Updates a book by id in database.
     * @param book - book to update.
     * @param id - book id.
     * @return - updated content of the book.
     * @return - the error message if there is a book in db (but it is deleted).
     */

    Book update(int id, Book book);

    /**
     *
     *
     * Common delete method (deletes the book beyond retrieve)
     *
     */

    //void removeBook(Book book);

    /**
     * Updates a book by id in database.
     * @param id - id of the book to delete
     * @return - the message of successful deletion.
     * @return - the error message if there is no book in db with the matching id.
     */

    void removeBook (int id);

    Book findBookByName (String name);

    List <Book> findBookByAuthor (String author);

    void restore (int id);

    Book getById (int id);

    Book addPosition (Integer id, Position position);

    Position getPositionByBookTitle (String name);
}
