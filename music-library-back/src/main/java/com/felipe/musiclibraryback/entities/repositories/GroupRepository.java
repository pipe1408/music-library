package com.felipe.musiclibraryback.entities.repositories;

import com.felipe.musiclibraryback.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    List<Group> findByNameAndShortName(String name, String shortName);

    List<Group> findByName(String name);

    List<Group> findByShortName(String shortName);
}
