package com.krylosov_books.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.dto.BookDto;
import com.krylosov_books.books.util.config.BookConverter;
import com.krylosov_books.books.web.BookController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooksApplication.class)
@AutoConfigureMockMvc
class BookControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;
    @MockBean
    BookController bookController;

    @Autowired
    private BookConverter converter;

    @Test
    public void createBook_success() throws Exception {
        Book book = Book.builder().name("Great book").author("Ivan")
                .pagesNumber(340).publisher("New Publisher").build();
        BookDto dto = converter.toDto(book);

        given(bookController.createBook(dto)).willReturn(dto);

        mockMvc.perform(post("/api/books/")
                        .contentType(MediaType.APPLICATION_JSON).content(asJsonString(book)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getAllBooks_success() throws Exception {

        Book book1 = Book.builder().name("New book").author("Ivan").build();
        Book book2 = Book.builder().name("This book").author("Whatever").build();

        List<Book> bookList = new ArrayList<>(Arrays.asList(book1, book2));

        Mockito.when(bookController.getAllBooks()).thenReturn(bookList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("New book")));
    }

    @Test
    public void findByTitle_success() throws Exception {
        Book book = Book.builder().name("New book").author("Ivan")
                .pagesNumber(340).publisher("New Publisher").build();

        BookDto dto = converter.toDto(book);

        Mockito.when(bookController.findByTitle(book.getName())).thenReturn(dto);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("name", "New book");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/").params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    private String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
