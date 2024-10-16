package com.felipe.musiclibraryback.service;

import com.felipe.musiclibraryback.entities.Group;
import com.felipe.musiclibraryback.entities.dto.GroupDTO;
import com.felipe.musiclibraryback.entities.repositories.GroupRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
        return groupRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.NOT_FOUND,
                        "No groups were found with the given ID"));
    }


    public Group createGroup(@Valid GroupDTO groupDTO) {
        List<Group> groupLookup = getGroups(groupDTO.name(), groupDTO.shortName());

        if (!groupLookup.isEmpty()) {
            return null;
        }
        Group group = new Group();
        group.setName(groupDTO.name());
        group.setShortName(groupDTO.shortName());
        group.setSize(0);
        return groupRepository.save(group);
    }

    public Group updateGroup(Integer id, @Valid GroupDTO groupDTO) {
        Group existingGroup = groupRepository.findById(id).orElse(null);
        if (existingGroup == null) {
            return null;
        }

        List<Group> groupLookup = getGroups(groupDTO.name(), groupDTO.shortName());
        if (!groupLookup.isEmpty() && !groupLookup.getFirst().getId().equals(id)) {
            return null;
        }

        existingGroup.setName(groupDTO.name());
        existingGroup.setShortName(groupDTO.shortName());
        return groupRepository.save(existingGroup);
    }


    public boolean deleteGroup(Integer id) {
        Group group = groupRepository.findById(id).orElse(null);
        if (group != null) {
            groupRepository.delete(group);
            return true;
        }
        return false;
    }
}
