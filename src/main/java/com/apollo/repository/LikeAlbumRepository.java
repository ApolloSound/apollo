package com.apollo.repository;

import com.apollo.domain.Album;
import com.apollo.domain.LikeAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the LikeAlbum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeAlbumRepository extends JpaRepository<LikeAlbum, Long> {

    @Query("select likeAlbum from LikeAlbum likeAlbum where likeAlbum.user.login = ?#{principal.username}")
    List<LikeAlbum> findByUserIsCurrentUser();

    @Query("select likeAlbum from LikeAlbum likeAlbum where likeAlbum.user.login = ?#{principal.username} and likeAlbum.album = :album ")
    Optional<LikeAlbum> findAlbumByUserIsCurrentUser(@Param("album") Album album);
}
