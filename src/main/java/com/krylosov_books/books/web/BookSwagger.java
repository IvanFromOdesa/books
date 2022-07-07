package com.krylosov_books.books.web;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.dto.BookDeleteDto;
import com.krylosov_books.books.dto.BookDto;
import com.krylosov_books.books.dto.BookRestoreDto;
import com.krylosov_books.books.dto.PositionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name="Book", description = "Book API")
public interface BookSwagger {

    @Operation(summary = "This is endpoint to add a new book.", description = "Create request to add a new book.", tags = {"Book"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new book is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified book request not found."),
            @ApiResponse(responseCode = "409", description = "Book already exists")})
    BookDto createBook(BookDto requestForSave);

    @Operation(summary = "This is endpoint to retrieve all books from db.", description = "Create request to retrieve all books.", tags = {"Book"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS.Books retrieved from the db."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified request not found.")})
    List<Book> getAllBooks();

    @Operation(summary = "This is endpoint to update an existing book.", description = "Create request to update a book by id.", tags = {"Book"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UPDATED. The book is successfully updated."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified book request not found."),
            @ApiResponse(responseCode = "409", description = "Book already exists")})
    Book refreshBook(Integer id, Book book);

    @Operation(summary = "This is endpoint to delete an existing book.", description = "Create request to delete a book by id.", tags = {"Book"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DELETED. The book is successfully deleted."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified book request not found.")})
    BookDeleteDto removeBookById(Integer id);

    @Operation(summary = "This is endpoint to find a book by title.", description = "Create request to find a book.", tags = {"Book"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FOUND. Book with the specified title is found."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified book request not found.")})
    BookDto findByTitle(String name);

    @Operation(summary = "This is endpoint to restore a deleted book by id.", description = "Create request to restore a book", tags = {"Book"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RESTORED. Book with the specified id is restored."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified book request not found.")})
    BookRestoreDto restoreBook(Integer id);

    @Operation(summary = "This is endpoint to find a list of books with the specified author.", description = "Create request to find a list of books.", tags = {"Book"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FOUND. Books with the specified author are found."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified book request not found.")})
    List<Book> findAnyByAuthor(String author);

    @Operation(summary = "This is endpoint to add a new position to the specified book. If the book already has a position this will update it accordingly.", description = "Create request to add a position for the book with the given id.", tags = {"Book"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS. A position is added to the book"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified book request not found.")})
    BookDto addBookPosition(Integer id, PositionDto positionDto);

    @Operation(summary = "This is endpoint to get the position of the specified book.", description = "Create request to get a position of the book by its name.", tags = {"Book"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS.The position of the specified book found."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified request not found.")})
    PositionDto getPositionByTitleOfBook(String name);
}
