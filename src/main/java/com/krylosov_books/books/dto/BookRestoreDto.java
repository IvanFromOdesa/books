package com.krylosov_books.books.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class BookRestoreDto {
    @Schema(description = "This is message that should be shown when user restores a book.")
    public String message = "The book was restored!";
}
