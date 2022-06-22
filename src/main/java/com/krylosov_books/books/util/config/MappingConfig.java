package com.krylosov_books.books.util.config;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.dto.BookDeleteDto;
import com.krylosov_books.books.dto.BookDto;
import com.krylosov_books.books.dto.BookRestoreDto;
import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;


public class MappingConfig implements OrikaMapperFactoryConfigurer{
    @Override
    public void configure(MapperFactory orikaMapperFactory) {

        orikaMapperFactory.classMap(Book.class, BookDto.class)
                .customize(new BookMapper())
                .byDefault()
                .register();

        orikaMapperFactory.classMap(Book.class, BookDto.class)
                .byDefault()
                .register();

        orikaMapperFactory.classMap(Book.class, BookDeleteDto.class)
                .byDefault()
                .register();

        orikaMapperFactory.classMap(Book.class, BookRestoreDto.class)
                .byDefault()
                .register();
    }
}
