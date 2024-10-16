package com.felipe.musiclibraryback.entities.repositories;

import com.felipe.musiclibraryback.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

}
