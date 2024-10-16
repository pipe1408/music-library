package com.felipe.musiclibraryback.entities.repositories;

import com.felipe.musiclibraryback.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, String> {

}
