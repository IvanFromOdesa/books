package com.krylosov_books.books.util.config;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.dto.BookDeleteDto;
import com.krylosov_books.books.dto.BookDto;
import com.krylosov_books.books.dto.BookRestoreDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {

    private final MapperFacade mapperFacade;

    public BookConverter(MapperFacade mapperFacade) {
        this.mapperFacade = mapperFacade;
    }

    public MapperFacade getMapperFacade(){
        return mapperFacade;
    }

    public BookDto toDto(Book entity){
        return mapperFacade.map(entity, BookDto.class);
    }

    public Book fromDto(BookDto dto){
        return mapperFacade.map(dto, Book.class);
    }

    public BookDeleteDto deleteDto (Book entity){
        return mapperFacade.map(entity, BookDeleteDto.class);
    }

    public BookRestoreDto restoreDto (Book entity){
        return mapperFacade.map(entity, BookRestoreDto.class);
    }
}
