package com.krylosov_books.books.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class BookDto {

    @NotBlank(message = "Name of the book cannot be empty!")
    @Size(min = 4, max = 30, message = "Name of the book must be between 4 and 30 characters long!")
    @Schema(description = "Name of the book.", example = "Can't hurt me", required = true)
    public String name;

    @NotBlank(message = "Author of the book cannot be empty!")
    @Size(min = 4, max = 35, message = "Author of the book must be between 5 and 35 characters long!")
    @Schema(description = "Author of the book.", example = "David Goggins", required = true)
    public String author;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Year of publication.", example = "2018-09-05")
    public LocalDate publicationYear;

    @NotNull(message = "Pages of the book cannot be null!")
    @Digits(integer = 4, fraction = 0)
    @Schema(description = "Number of pages in the book.", example = "350")
    public int pagesNumber;

    @NotBlank(message = "Publisher of the book cannot be empty!")
    @Size(min = 4, max = 30, message = "Publisher of the book must be between 4 and 30 characters long!")
    @Schema(description = "Publisher of the book.", example = "Lioncrest")
    public String publisher;
}
