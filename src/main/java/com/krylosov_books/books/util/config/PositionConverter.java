package com.krylosov_books.books.util.config;

import com.krylosov_books.books.domain.Position;
import com.krylosov_books.books.dto.PositionDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
public class PositionConverter {
    private final MapperFacade mapperFacade;

    public PositionConverter(MapperFacade mapperFacade) {
        this.mapperFacade = mapperFacade;
    }

    public MapperFacade getMapperFacade(){
        return mapperFacade;
    }

    public PositionDto toDto(Position entity){
        return mapperFacade.map(entity, PositionDto.class);
    }

    public Position fromDto(PositionDto dto){
        return mapperFacade.map(dto, Position.class);
    }
}
