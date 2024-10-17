package com.felipe.musiclibraryback.controller;

import com.felipe.musiclibraryback.service.GroupService;
import com.felipe.musiclibraryback.entities.Group;
import com.felipe.musiclibraryback.entities.dto.GroupDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;
    private final Logger logger;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String shortName
    ) {
        logger.info("Received request for getAllGroups");
        return ResponseEntity.ok(groupService.getGroups(name, shortName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable int id) {
        logger.info("Received request for getGroupById");
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping
    public ResponseEntity<Group> postGroup(@RequestBody @Valid GroupDTO groupDTO) {
        logger.info("Received request for postGroup");
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.createGroup(groupDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> putGroup(@PathVariable int id, @RequestBody @Valid GroupDTO groupDTO) {
        logger.info("Received request for putGroup");
        return ResponseEntity.ok(groupService.updateGroup(id, groupDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable int id) {
        logger.info("Received request for deleteGroup");
        groupService.deleteGroup(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
