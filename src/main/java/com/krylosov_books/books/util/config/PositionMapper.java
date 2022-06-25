package com.krylosov_books.books.util.config;

import com.krylosov_books.books.domain.Position;
import com.krylosov_books.books.dto.PositionDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class PositionMapper extends CustomMapper<Position, PositionDto> {

    @Override
    public void mapAtoB(Position position, PositionDto positionDto, MappingContext context) {
        super.mapAtoB(position, positionDto, context);
    }
}
