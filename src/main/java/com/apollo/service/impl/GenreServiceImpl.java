package com.apollo.service.impl;

import com.apollo.domain.Genre;
import com.apollo.repository.GenreRepository;
import com.apollo.service.GenreService;
import com.apollo.service.dto.GenreDTO;
import com.apollo.service.mapper.GenreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Genre}.
 */
@Service
public class GenreServiceImpl implements GenreService {

    private final Logger log = LoggerFactory.getLogger(GenreServiceImpl.class);

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    /**
     * Save a genre.
     *
     * @param genreDTO the entity to save.
     * @return the persisted entity.
     */
    public GenreDTO save(GenreDTO genreDTO) {
        log.debug("Request to save Genre : {}", genreDTO);
        Genre genre = genreMapper.toEntity(genreDTO);
        genre = genreRepository.save(genre);
        return genreMapper.toDto(genre);
    }

    /**
     * Get all the genres.
     *
     * @return the list of entities.
     */
    public List<GenreDTO> findAll() {
        log.debug("Request to get all Genres");
        return genreRepository.findAll()
            .stream()
            .map(genreMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get genres by list of Ids.
     *
     * @return the list of entities.
     */
    public List<GenreDTO> findAllById(List<Long> genresIds) {
        log.debug("Request to get genres by list of Ids");
        return genreRepository.findAllById(genresIds)
            .stream()
            .map(genreMapper::toDto)
            .collect(Collectors.toList());
    }


    /**
     * Get one genre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<GenreDTO> findOne(Long id) {
        log.debug("Request to get Genre : {}", id);
        return genreRepository.findById(id)
            .map(genreMapper::toDto);
    }

    /**
     * Delete the genre by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Genre : {}", id);
        genreRepository.deleteById(id);
    }
}
