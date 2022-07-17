package com.krylosov_books.books.service;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.domain.Position;
import com.krylosov_books.books.util.exception.ResourceAlreadyExistsException;
import com.krylosov_books.books.util.exception.ResourceNotFoundException;
import com.krylosov_books.books.util.exception.ResourceWasDeletedException;

import java.util.Collection;
import java.util.List;


public interface BookService {
    /**
     * Persists new book to database.
     *
     * @param book - book to persist.
     * @return - make book.
     * @throws ResourceAlreadyExistsException if the book with the given name already exists
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
     * @throws ResourceWasDeletedException if the specified book was previously deleted
     * @throws ResourceNotFoundException - if the book with the specified id does not exist
     */

    Book update(int id, Book book);

    /**
     * Removes a book by id in database.
     * @param id - book id.
     * @throws ResourceWasDeletedException if the specified book was previously deleted
     * @throws ResourceNotFoundException - if the book with the specified id does not exist
     */

    void removeBook (int id);

    /**
     * Retrieves a book by name from database.
     * @param name - book name.
     * @return - book with the specified name
     * @throws ResourceWasDeletedException if the specified book was previously deleted
     * @throws ResourceNotFoundException - if the book with the specified name does not exist
     */

    Book findBookByName (String name);

    /**
     * Retrieves a list of books with the specified author from database.
     * @param author - book author.
     * @return - list
     * @throws ResourceNotFoundException - if the book with the specified author does not exist
     *                                     or all of his books are deleted
     */

    List<Book> findBookByAuthor (String author);

    /**
     * Restores a deleted book by id in database.
     * @param id - book id.
     * @throws ResourceNotFoundException - if the book with the specified id does not exist
     */

    void restore (int id);

    /**
     * Retrieves a book by id from database.
     * @param id - book id.
     * @return - book with the specified id
     * @throws ResourceNotFoundException - if the book with the specified id does not exist
     */

    Book getById (int id);

    /**
     * Removes a book by id in database.
     * @param id - book id.
     * @param position - book position
     * @return - book with the specified position
     * @throws ResourceWasDeletedException if the specified book was previously deleted
     * @throws ResourceNotFoundException - if the book with the specified id does not exist
     */

    Book addPosition (Integer id, Position position);

    /**
     * Removes a book by id in database.
     * @param name - book name.
     * @return - position of the specified book
     * @throws ResourceWasDeletedException if the specified book was previously deleted
     * @throws ResourceNotFoundException - if the book with the specified name does not exist
     */

    Position getPositionByBookTitle (String name);
}
