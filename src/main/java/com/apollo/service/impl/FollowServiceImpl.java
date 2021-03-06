package com.apollo.service.impl;

import com.apollo.domain.FollowPlaylist;
import com.apollo.domain.FollowUser;
import com.apollo.domain.User;
import com.apollo.repository.FollowPlaylistRepository;
import com.apollo.repository.FollowUserRepository;
import com.apollo.repository.PlaylistRepository;
import com.apollo.service.FollowService;
import com.apollo.service.UserService;
import com.apollo.service.dto.FollowDTO;
import com.apollo.service.dto.PlaylistDTO;
import com.apollo.service.dto.UserDTO;
import com.apollo.service.exception.BadFollowerException;
import com.apollo.service.exception.PlaylistNotFoundException;
import com.apollo.service.exception.UserNotFoundException;
import com.apollo.service.mapper.PlaylistMapper;
import com.apollo.service.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class FollowServiceImpl implements FollowService {

    private final UserService userService;

    private final PlaylistRepository playlistRepository;

    private final PlaylistMapper playlistMapper;

    private final FollowPlaylistRepository followPlaylistRepository;

    private final FollowUserRepository followUserRepository;

    private final UserMapper userMapper;

    public FollowServiceImpl(UserService userService,
                             PlaylistRepository playlistRepository,
                             PlaylistMapper playlistMapper,
                             FollowPlaylistRepository followPlaylistRepository,
                             FollowUserRepository followUserRepository,
                             UserMapper userMapper) {
        this.userService = userService;
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
        this.followPlaylistRepository = followPlaylistRepository;
        this.followUserRepository = followUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public FollowDTO toggleFollowPlaylist(Long playlistId) {
        final User currentUser = userService.getUserWithAuthorities();

        checkIfPlaylistExists(playlistId);

        final Optional<FollowPlaylist> followedPlaylist = followPlaylistRepository.findByIdAndCurrentUser(playlistId);

        FollowDTO followDTO = new FollowDTO();


        if (followedPlaylist.isPresent()) {
            followPlaylistRepository.delete(followedPlaylist.get());
            followDTO.setFollowed(false);
        } else {
            FollowPlaylist followPlaylist = new FollowPlaylist();
            followPlaylist.setUser(currentUser);
            followPlaylist.setPlaylist(playlistMapper.fromId(playlistId));
            followPlaylistRepository.save(followPlaylist);
            followDTO.setFollowed(true);
        }


        return followDTO;
    }

    @Override
    public FollowDTO toggleFollowUser(String login) {
        final User currentUser = userService.getUserWithAuthorities();

        User userToFollow = userService.getUserWithAuthoritiesByLogin(login)
            .orElseThrow(UserNotFoundException::new);

        checkIfFollowUserIsTheCurrentUser(currentUser, userToFollow);

        FollowDTO followDTO = new FollowDTO();

        final Optional<FollowUser> followedUser = followUserRepository.findIfFollowedUserIsFollowedByCurrentUser(login);

        if (followedUser.isPresent()) {
            followUserRepository.delete(followedUser.get());
            followDTO.setFollowed(false);
        } else {
            FollowUser followUser = new FollowUser();
            followUser.setUser(currentUser);
            followUser.setFollowed(userToFollow);
            followUserRepository.save(followUser);
            followDTO.setFollowed(true);
        }

        return followDTO;
    }

    private void checkIfFollowUserIsTheCurrentUser(User currentUser, User userToFollow) {
        if (currentUser.getLogin().equals(userToFollow.getLogin())) {
            throw new BadFollowerException();
        }
    }

    @Override
    public void deleteFollowersByPlaylist(Long playlistId) {
        checkIfPlaylistExists(playlistId);
        followPlaylistRepository.deleteByPlaylistId(playlistId);
    }

    @Override
    @Transactional
    public List<UserDTO> findFollowersOfCurrentUser() {
        return followUserRepository.findFollowersByCurrentUser()
            .stream()
            .map(userMapper::userToUserDTO)
            .collect(toList());
    }

    @Override
    @Transactional
    public List<UserDTO> findFollowingUsersByCurrentUser() {
        return followUserRepository.findFollowingsByCurrentUser()
            .stream()
            .map(userMapper::userToUserDTO)
            .collect(toList());
    }

    @Override
    @Transactional
    public List<PlaylistDTO> findFollowingPlaylistsByCurrentUser() {
        return followPlaylistRepository.findPlaylistFollowedByCurrentUser()
            .stream()
            .map(playlistMapper::toDto)
            .collect(toList());
    }

    @Override
    public FollowDTO checkCurrentUserFollowUser(String login) {
        final Optional<FollowUser> followedUser = followUserRepository.findIfFollowedUserIsFollowedByCurrentUser(login);

        if (followedUser.isPresent()) {
            return new FollowDTO(true);
        } else {
            return new FollowDTO(false);
        }
    }


    @Override
    public FollowDTO checkCurrentUserFollowPlaylist(Long playlistId) {
        final Optional<FollowPlaylist> followPlaylist = followPlaylistRepository.findByIdAndCurrentUser(playlistId);

        if (followPlaylist.isPresent()) {
            return new FollowDTO(true);
        } else {
            return new FollowDTO(false);
        }
    }

    @Override
    public List<UserDTO> findFollowersByLogin(String login) {
        return followUserRepository.findFollowersByLogin(login)
            .stream()
            .map(userMapper::userToUserDTO)
            .collect(toList());
    }

    @Override
    public List<UserDTO> findFollowingsByLogin(String login) {
        return followUserRepository.findFollowingsByLogin(login)
            .stream()
            .map(userMapper::userToUserDTO)
            .collect(toList());
    }

    private void checkIfPlaylistExists(Long playlistId) {
        playlistRepository.findById(playlistId)
            .orElseThrow(PlaylistNotFoundException::new);
    }

}
