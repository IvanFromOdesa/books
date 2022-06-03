package com.krylosov_books.books.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties("deleted")
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;

    private String author;

    private LocalDateTime publicationYear;

    private int pagesNumber;

    private String publisher;

    private Boolean isDeleted;


    public void setId(Integer id) {this.id = id;}

    public Integer getId() {return id;}

    public void setName(String name) {this.name = name;}

    public String getName() {return name;}

    public void setAuthor(String author) {this.author = author;}

    public String getAuthor() {return author;}

    public void setPublicationYear(LocalDateTime publicationYear) {this.publicationYear = publicationYear;}

    public LocalDateTime getPublicationYear() {return publicationYear;}

    public void setPagesNumber(int pagesNumber) {this.pagesNumber = pagesNumber;}

    public int getPagesNumber() {return pagesNumber;}

    public void setPublisher(String publisher) {this.publisher = publisher;}

    public String getPublisher() {return publisher;}

    public void setDeleted(Boolean deleted) {isDeleted = deleted;}

    public Boolean getDeleted() {return isDeleted;}
}
