package com.apollo.service;

import com.apollo.service.dto.LikeDTO;

/**
 * Service Interface for managing likes in Albums and Tracks.
 */
public interface LikeService {

    /**
     * Method to like or dislike a Track by "id"
     *
     * @param trackId the id of the Track
     * @return a LikeDTO with the liked boolean
     */
    LikeDTO toggleLikeTrack(Long trackId);

    /**
     * Checks if current user has liked the Track by "id"
     * @param id the id of the Track
     * @return a LikeDTO with true if the user has liked or false if the track isn't found or not liked
     */
    LikeDTO checkLikeTrack(Long id);
}
