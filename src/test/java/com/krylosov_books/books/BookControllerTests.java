package com.krylosov_books.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.dto.BookDto;
import com.krylosov_books.books.util.config.BookConverter;
import com.krylosov_books.books.web.BookController;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooksApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    @Autowired
    WebApplicationContext webApplicationContext;

    @Test
    @WithMockUser
    @Disabled
    public void createBook_success() throws Exception {
        Book book = Book.builder().name("Great book").author("Ivan")
                .pagesNumber(340).publisher("New Publisher").build();
        BookDto dto = converter.toDto(book);

        given(bookController.createBook(dto)).willReturn(dto);

        mockMvc.perform(post("/api/books/create")
                        .contentType(MediaType.APPLICATION_JSON).content(asJsonString(book)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void getAllBooks_success() throws Exception {

        Book book1 = Book.builder().name("New book").author("Ivan").build();
        Book book2 = Book.builder().name("This book").author("Whatever").build();

        List<Book> bookList = new ArrayList<>(Arrays.asList(book1, book2));

        Mockito.when(bookController.getAllBooks()).thenReturn(bookList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("New book")));
    }

    @Test
    @WithMockUser
    public void findByTitle_success() throws Exception {

        Book book = Book.builder().name("New book").author("Ivan")
                .pagesNumber(340).publisher("New Publisher").build();

        BookDto dto = converter.toDto(book);

        Mockito.when(bookController.findByTitle(book.getName())).thenReturn(dto);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("name", "New book");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/byTitle").params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }
    private String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
