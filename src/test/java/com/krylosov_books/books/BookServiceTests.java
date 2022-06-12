package com.krylosov_books.books;

import com.krylosov_books.books.domain.Book;
import com.krylosov_books.books.repository.BookRepository;
import com.krylosov_books.books.service.BookServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceBean bookServiceBean;

    @Test
    public void whenSaveBook_shouldReturnBook() {
        Book book = Book.builder().name("Super Book").author("JJ Redbrick").build();

        when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(book);
        Book createdBook = (Book) bookServiceBean.create(book);

        assertThat(createdBook.getName()).isSameAs(book.getName());
        verify(bookRepository).save(book);
    }

    @Test
    public void whenGivenName_shouldReturnBook_ifFound() {
        Book book = new Book();
        book.setName("Super Book");

        when(bookRepository.findByName(book.getName())).thenReturn(book);

        Book expectedBook = (Book) bookServiceBean.findBookByName("Super Book");

        assertThat(expectedBook).isSameAs(book);
        verify(bookRepository).findByName(book.getName());
    }

    @Test
    public void whenGivenAuthor_shouldReturnListOfBooksWithGivenAuthor_ifFound(){
        Book book1 = Book.builder().name("Super Book").author("JJ Redbrick").build();
        Book book2 = Book.builder().name("New Book").author("JJ Redbrick").build();
        Book book3 = Book.builder().name("Just a Book").author("Eric Hansen").build();

        int howManyBooksWithTheGivenAuthor = 0;

        List <Book> list = new ArrayList<>();
        list.add(book1);
        list.add(book2);
        list.add(book3);

        for(Book index: list){
            if(index.getAuthor().equals("JJ Redbrick")){
                howManyBooksWithTheGivenAuthor++;
            }
        }

        Mockito.doAnswer(invocation -> {
            list.removeIf(checkBook -> !Objects.equals(checkBook.getAuthor(), "JJ Redbrick"));
            return list;
        }).when(bookRepository).findByAuthor(Mockito.eq("JJ Redbrick"));

        ArrayList<Book> expectedList = null;
        Exception exception = null;

        try{
            expectedList = (ArrayList<Book>) bookServiceBean.findBookByAuthor("JJ Redbrick");
        } catch (ClassCastException e) {
            exception = e;
        }

        assertNull(exception); // if there were no books with the given author (findBookByAuthor() returned String)
        assertThat(expectedList.size()).isEqualTo(howManyBooksWithTheGivenAuthor); // check if all the books with the given name are in expectedList

        boolean isAllBooksWithTheGivenAuthor = false;
        for(Book index: expectedList){
            if (index.getAuthor().equals("JJ Redbrick")) {
                isAllBooksWithTheGivenAuthor = true;
            }
        }

        assertThat(isAllBooksWithTheGivenAuthor).isEqualTo(true); // check if all the books' author is "JJ Redbrick"
        verify(bookRepository).findByAuthor("JJ Redbrick");
    }

    /**
     * Check return type must be an instance of String.
     *
     *
     * bookServiceBean.findBookByName returns String: "The book with the given name " + name + " does not exist!".
     */
    @Test
    public void should_return_string_when_book_doesnt_exist() {
        Book book = new Book();
        book.setName("New adventures");

        given(bookRepository.findByName(anyString())).willReturn(null);
        Object isString = bookServiceBean.findBookByName(book.getName());
        assertThat(isString).isInstanceOf(String.class);
    }
}
