package com.krylosov_books.books.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@JsonIgnoreProperties("deleted")
@Table(name="books", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="author")
    private String author;

    @Column(name="publication_year")
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate publicationYear;

    @Column(name="pages_number")
    private int pagesNumber;

    @Column(name="publisher")
    private String publisher;

    @Column(name="is_deleted")
    private Boolean isDeleted;

    public void setId(Integer id) {this.id = id;}

    public Integer getId() {return id;}

    public void setName(String name) {this.name = name;}

    public String getName() {return name;}

    public void setAuthor(String author) {this.author = author;}

    public String getAuthor() {return author;}

    public void setPublicationYear(LocalDate publicationYear) {this.publicationYear = publicationYear;}

    public LocalDate getPublicationYear() {return publicationYear;}

    public void setPagesNumber(int pagesNumber) {this.pagesNumber = pagesNumber;}

    public int getPagesNumber() {return pagesNumber;}

    public void setPublisher(String publisher) {this.publisher = publisher;}

    public String getPublisher() {return publisher;}

    public void setDeleted(Boolean deleted) {isDeleted = deleted;}

    public Boolean getDeleted() {return isDeleted;}
}
