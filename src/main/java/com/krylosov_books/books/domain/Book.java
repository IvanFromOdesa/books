package com.krylosov_books.books.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Id of the book", pattern = "identity")
    private Integer id;

    @Column(name="name")
    @Schema(description = "Name of the book.", example = "Can't hurt me", required = true)
    private String name;

    @Column(name="author")
    @Schema(description = "Author of the book.", example = "David Goggins", required = true)
    private String author;

    @Column(name="publication_year")
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Year of publication.", example = "2018-09-05")
    private LocalDate publicationYear;

    @Column(name="pages_number")
    @Schema(description = "Number of pages in the book.", example = "350")
    private int pagesNumber;

    @Column(name="publisher")
    @Schema(description = "Publisher of the book.", example = "Lioncrest")
    private String publisher;

    @Column(name="is_deleted")
    @Schema(hidden = true)
    private Boolean isDeleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id", referencedColumnName = "id_position")
    private Position position;

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

    public Position getPosition() {return position;}

    public void setPosition(Position position) {this.position = position;}

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                ", pagesNumber=" + pagesNumber +
                ", publisher='" + publisher + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
