package com.krylosov_books.books.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PositionDto {

    @Schema(description = "Location of the book", example = "Front row shelf no.10, place no.3")
    @Size(max = 40, message = "Name of location cannot be bigger than 40 symbols.")
    @NotBlank(message = "Location cannot be absent. Set location for the book.")
    public String location;

}
