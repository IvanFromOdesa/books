package com.krylosov_books.books.repository;

import com.krylosov_books.books.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PositionRepository extends JpaRepository<Position, Integer> {

    @Query(value = "SELECT * FROM position\n" +
            "WHERE id_position IN\n" +
            "\t(SELECT position_id FROM books\n" +
            "\tWHERE name = :name)", nativeQuery = true)
    @Transactional
    Position getPositionByBookName(String name);
}
