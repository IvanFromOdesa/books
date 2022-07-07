package com.krylosov_books.books.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "position")
@Getter
@Setter
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_position")
    private Integer idPosition;

    @Column(name = "location")
    private String location;

    @JsonManagedReference
    @OneToOne(mappedBy = "position")
    private Book book;
}
