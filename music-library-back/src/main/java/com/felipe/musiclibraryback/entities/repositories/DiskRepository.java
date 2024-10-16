package com.felipe.musiclibraryback.entities.repositories;

import com.felipe.musiclibraryback.entities.Disk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiskRepository extends JpaRepository<Disk, String> {

}
