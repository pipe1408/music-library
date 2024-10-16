package com.felipe.musiclibraryback.controller;

import com.felipe.musiclibraryback.GroupService;
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
    public ResponseEntity<List<Group>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @PostMapping
    public ResponseEntity<Group> postGroup(@RequestBody @Valid GroupDTO groupDTO) {
        Group group = groupService.createGroup(groupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }
}
