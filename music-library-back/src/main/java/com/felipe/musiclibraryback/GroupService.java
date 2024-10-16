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

    public List<Group> getGroups(String name, String shortName) {
        if (name != null && shortName != null) {
            return groupRepository.findByNameAndShortName(name, shortName);
        } else if (name != null) {
            return groupRepository.findByName(name);
        } else if (shortName != null) {
            return groupRepository.findByShortName(shortName);
        } else {
            return groupRepository.findAll();
        }
    }

    public Group getGroupById(int id) {
        return groupRepository.findById(id).orElse(null);
    }

    public Group createGroup(@Valid GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.name());
        group.setShortName(groupDTO.shortName());
        group.setSize(0);
        return groupRepository.save(group);
    }
}
