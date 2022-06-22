package com.krylosov_books.books.util.config;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.dto.BookDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class BookMapper extends CustomMapper<Book, BookDto> {
    @Override
    public void mapBtoA(BookDto bookDto, Book book, MappingContext context) {
        super.mapBtoA(bookDto, book, context);
    }
}
