package com.felipe.musiclibraryback;

import com.felipe.musiclibraryback.entities.Group;
import com.felipe.musiclibraryback.entities.dto.GroupDTO;
import com.felipe.musiclibraryback.entities.repositories.GroupRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group createGroup(@Valid GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.name());
        group.setShortName(groupDTO.shortName());
        group.setSize(0);
        return groupRepository.save(group);
    }
}
