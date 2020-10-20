package com.apollo.service.impl;

import com.apollo.domain.Playlist;
import com.apollo.domain.Track;
import com.apollo.repository.PlaylistRepository;
import com.apollo.repository.TrackRepository;
import com.apollo.repository.UserRepository;
import com.apollo.service.UserDeleteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDeleteServiceImpl implements UserDeleteService {

    private final UserRepository userRepository;

    private final TrackRepository trackRepository;

    private final PlaylistRepository playlistRepository;

    public UserDeleteServiceImpl(UserRepository userRepository,
                                 TrackRepository trackRepository,
                                 PlaylistRepository playlistRepository) {
        this.userRepository = userRepository;
        this.trackRepository = trackRepository;
        this.playlistRepository = playlistRepository;
    }

    @Transactional
    @Override
    public void removeUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            trackRepository.findAllByUserLogin(login)
                .forEach(this::removeTrackInAnotherPlaylist);
            userRepository.delete(user);
        });
    }

    public void removeTrackInAnotherPlaylist(Track track) {
        final List<Playlist> playlistsWithTrack = playlistRepository.findByTracksContains(track);
        playlistsWithTrack.forEach(playlist -> removeTrackOfPlaylist(playlist, track));
    }

    public void removeTrackOfPlaylist(Playlist playlist, Track track) {
        playlist.getTracks().remove(track);
        playlistRepository.save(playlist);
    }

}
