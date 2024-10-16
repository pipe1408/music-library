package com.felipe.musiclibraryback.entities.repositories;

import com.felipe.musiclibraryback.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

}
