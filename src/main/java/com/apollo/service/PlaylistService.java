package com.apollo.service;

import com.apollo.service.dto.PlaylistDTO;
import com.apollo.service.dto.PlaylistRequestDTO;

import java.util.List;

/**
 * Service Interface for managing {@link com.apollo.domain.Playlist}.
 */
public interface PlaylistService {

    /**
     * Save a playlist.
     *
     * @param playlistRequestDTO of the entity to save.
     * @return the persisted entity.
     */
    PlaylistDTO save(PlaylistRequestDTO playlistRequestDTO);

    /**
     * Get all the playlists.
     *
     * @return the list of entities.
     */
    List<PlaylistDTO> findAll();

    /**
     * Get the "id" playlist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    PlaylistDTO findOne(Long id);

    /**
     * Delete the "id" playlist.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Method to find all the playlists of the current user
     *
     * @return the list of playlists
     */
    List<PlaylistDTO> findAllByCurrentUser();

    /**
     * Method to find a playlist by the current user and by id
     * @param id the id of the playlist
     * @return the playlist
     */
    PlaylistDTO findOwnPlaylistById(Long id);

    /**
     * Method to find playlist by user login
     *
     * @param login the login of the user
     * @return the list of playlists
     */
    List<PlaylistDTO> findAllByUserLogin(String login);

    /**
     * Method to delete a playlist by user login
     * @param login the login of the user
     */
    void deleteByUser(String login);
}
