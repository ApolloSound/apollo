package com.apollo.service.impl;

import com.apollo.domain.*;
import com.apollo.repository.TrackRepository;
import com.apollo.service.QueryService;
import com.apollo.service.dto.TrackDTO;
import com.apollo.service.dto.criteria.TrackCriteriaDTO;
import com.apollo.service.dto.criteria.UserTrackCriteriaDTO;
import com.apollo.service.mapper.TrackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.SetJoin;
import java.util.List;

import static javax.persistence.criteria.JoinType.INNER;

/**
 * Service for executing complex queries for {@link Track} entities in the database.
 * The main input is a {@link TrackCriteriaDTO} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TrackDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrackQueryService implements QueryService<TrackDTO, TrackCriteriaDTO> {

    private final Logger log = LoggerFactory.getLogger(TrackQueryService.class);

    private final TrackRepository trackRepository;

    private final TrackMapper trackMapper;

    public TrackQueryService(TrackRepository trackRepository, TrackMapper trackMapper) {
        this.trackRepository = trackRepository;
        this.trackMapper = trackMapper;
    }


    /**
     * Return a {@link List} of {@link com.apollo.service.dto.TrackDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrackDTO> findByCriteria(TrackCriteriaDTO criteria, Pageable pageable) {
        log.debug("Find tracks by criteria : {}", criteria);
        final Specification<Track> specification = createSpecification(criteria);

        Page<Track> tracks = trackRepository.findAll(specification, pageable);

        return transformTracks(tracks);
    }

    /**
     * Return a {@link List} of {@link com.apollo.service.dto.TrackDTO} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrackDTO> findByCriteria(UserTrackCriteriaDTO criteria, String login, Pageable pageable) {
        log.debug("Find tracks of {} criteria : {}", login, criteria);
        final Specification<Track> specification = createSpecification(criteria, login);

        Page<Track> tracks = trackRepository.findAll(specification, pageable);

        return transformTracks(tracks);
    }

    /**
     * Function to convert {@link TrackCriteriaDTO} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Track> createSpecification(TrackCriteriaDTO criteria) {
        Specification<Track> specification = Specification.where(null);

        if (criteria != null) {
            if (criteria.getRecent() != null && criteria.getRecent()) {
                specification = specification.and(sortByCreated());
            }
            if (criteria.getLiked() != null && criteria.getLiked()) {
                specification = specification.and(sortByMostLiked());
            }
            if (criteria.getPlayed() != null) {
                specification = specification.and(sortByMostPlayed());
            }
            if (criteria.getGenre() != null) {
                specification = specification.and(byGenres(criteria.getGenre()));
            }
        }
        return specification;
    }

    protected Specification<Track> createSpecification(UserTrackCriteriaDTO criteria, String login) {
        Specification<Track> specification = Specification.where(null);

        if (criteria != null) {
            specification = specification.and(findByUserLogin(login));
            if (criteria.getPopular() != null && criteria.getPopular()) {
                specification = specification.and(sortByMostPlayed());
            }
        }
        return specification;
    }

    private Specification<Track> findByUserLogin(String login) {
        return (root, query, builder) -> {
            final Join<Track, User> userJoin = root.join(Track_.user, INNER);
            return builder.equal(userJoin.get(User_.login), login);
        };
    }

    private Specification<Track> sortByMostPlayed() {

        return (root, query, builder) -> {
            SetJoin<Track, Playback> playbacks = root.join(Track_.playbacks, INNER);
            query.groupBy(playbacks.get(Playback_.track));

            final Order order = builder.desc(builder.count(playbacks.get(Playback_.id)));

            return query.orderBy(order).getRestriction();
        };
    }

    private Specification<Track> sortByMostLiked() {

        return (root, query, builder) -> {
            SetJoin<Track, LikeTrack> likeTrack = root.join(Track_.likeTracks, INNER);
            query.groupBy(likeTrack.get(LikeTrack_.track));

            final Order order = builder.desc(builder.count(likeTrack.get(LikeTrack_.id)));

            return query.orderBy(order).getRestriction();
        };
    }

    private Specification<Track> sortByCreated() {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final Order order = criteriaBuilder.desc(root.get(Track_.createdAt));
            return criteriaQuery.orderBy(order).getRestriction();
        };
    }

    private Specification<Track> byGenres(String genre) {
        return (root, query, builder) -> {
            final Join<Track, Genre> genreJoin = root.join(Track_.genres);
            final Path<String> genreName = genreJoin.get(Genre_.name);
            return query.where(builder.equal(genreName, genre)).getGroupRestriction();
        };
    }

    private Page<TrackDTO> transformTracks(Page<Track> tracks) {
        return tracks.map(trackMapper::toDto);
    }
}
