package com.apollo.service.mapper;

import com.apollo.domain.Genre;
import com.apollo.service.dto.GenreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Genre} and its DTO {@link GenreDTO}.
 */
@Mapper(componentModel = "spring")
public interface GenreMapper extends EntityMapper<GenreDTO, Genre> {

    @Mapping(target = "popularity", ignore = true)
    Genre toEntity(GenreDTO genreDTO);

    GenreDTO toDto(Genre genre);

    default Genre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setId(id);
        return genre;
    }
}
