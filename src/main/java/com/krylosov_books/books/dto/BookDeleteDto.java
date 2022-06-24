package com.krylosov_books.books.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class BookDeleteDto {
    @Schema(description = "This is message that should be shown when user deletes a book")
    public String message = "The book with this id was deleted!";
}
