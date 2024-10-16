package com.felipe.musiclibraryback.controller;

import com.felipe.musiclibraryback.service.GroupService;
import com.felipe.musiclibraryback.entities.Group;
import com.felipe.musiclibraryback.entities.dto.GroupDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String shortName
    ) {
        List<Group> groups = groupService.getGroups(name, shortName);
        return ResponseEntity.ok(groups);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable int id) {
        Group group = groupService.getGroupById(id);

        if (group == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(group);
    }

    @PostMapping
    public ResponseEntity<Group> postGroup(@RequestBody @Valid GroupDTO groupDTO) {
        Group group = groupService.createGroup(groupDTO);

        if (group == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }
}
