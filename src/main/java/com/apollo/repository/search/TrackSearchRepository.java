package com.apollo.repository.search;

import com.apollo.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data repository for searching tracks
 */
public interface TrackSearchRepository extends JpaRepository<Track, Long> {

    @Query("select track from Track track where track.name like %:keyword%")
    List<Track> search(@Param("keyword") String keyword);
}
