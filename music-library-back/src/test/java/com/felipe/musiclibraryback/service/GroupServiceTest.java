package com.felipe.musiclibraryback.service;

import com.felipe.musiclibraryback.entities.Group;
import com.felipe.musiclibraryback.entities.dto.GroupDTO;
import com.felipe.musiclibraryback.entities.repositories.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    private Group existingGroup;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        existingGroup = new Group();
        existingGroup.setId(1); // Set the ID of the existing group
        existingGroup.setName("Some Country");
        existingGroup.setShortName("SC");
    }

    @Test
    void getGroups_ShouldReturnGroups_WhenBothNameAndShortNameProvided() {
        // Given
        String name = "Some Country";
        String shortName = "SC";
        Group expectedGroup = new Group();
        expectedGroup.setName(name);
        expectedGroup.setShortName(shortName);
        List<Group> expectedGroups = Collections.singletonList(expectedGroup);
        when(groupRepository.findByNameAndShortName(name, shortName)).thenReturn(expectedGroups);

        // When
        List<Group> actualGroups = groupService.getGroups(name, shortName);

        // Then
        assertEquals(expectedGroups, actualGroups);
        verify(groupRepository).findByNameAndShortName(name, shortName);
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void getGroups_ShouldReturnGroups_WhenOnlyNameProvided() {
        // Given
        String name = "Some Country";
        Group expectedGroup = new Group();
        expectedGroup.setName(name);
        List<Group> expectedGroups = Collections.singletonList(expectedGroup);
        when(groupRepository.findByName(name)).thenReturn(expectedGroups);

        // When
        List<Group> actualGroups = groupService.getGroups(name, null);

        // Then
        assertEquals(expectedGroups, actualGroups);
        verify(groupRepository).findByName(name);
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void getGroups_ShouldReturnGroups_WhenOnlyShortNameProvided() {
        // Given
        String shortName = "SC";
        Group expectedGroup = new Group();
        expectedGroup.setShortName(shortName);
        List<Group> expectedGroups = Collections.singletonList(expectedGroup);
        when(groupRepository.findByShortName(shortName)).thenReturn(expectedGroups);

        // When
        List<Group> actualGroups = groupService.getGroups(null, shortName);

        // Then
        assertEquals(expectedGroups, actualGroups);
        verify(groupRepository).findByShortName(shortName);
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void getGroups_ShouldReturnAllGroups_WhenNeitherNameNorShortNameProvided() {
        // Given
        Group expectedGroup = new Group();
        expectedGroup.setName("Some Country");
        expectedGroup.setShortName("SC");
        List<Group> expectedGroups = Collections.singletonList(expectedGroup);
        when(groupRepository.findAll()).thenReturn(expectedGroups);

        // When
        List<Group> actualGroups = groupService.getGroups(null, null);

        // Then
        assertEquals(expectedGroups, actualGroups);
        verify(groupRepository).findAll();
        verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void getGroupById_ShouldReturnGroup_WhenGroupExists() {
        // Given
        int id = 1;
        Group expectedGroup = new Group();
        expectedGroup.setId(id);
        expectedGroup.setName("Some Country");
        expectedGroup.setShortName("SC");

        when(groupRepository.findById(id)).thenReturn(Optional.of(expectedGroup));

        // When
        Group actualGroup = groupService.getGroupById(id);

        // Then
        assertEquals(expectedGroup, actualGroup);
        verify(groupRepository).findById(id);
    }

    @Test
    void getGroupById_ShouldThrowHttpClientErrorException_WhenGroupDoesNotExist() {
        // Given
        int id = 1;
        when(groupRepository.findById(id)).thenReturn(Optional.empty());

        // When
        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> groupService.getGroupById(id)
        );

        // Then
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 No groups were found with the given ID", exception.getMessage());
        verify(groupRepository).findById(id);
    }

    @Test
    void createGroup_ShouldReturnGroup_WhenGroupDoesNotExist() {
        // Given
        GroupDTO groupDTO = new GroupDTO("Some Country", "SC");
        List<Group> groupLookup = Collections.emptyList(); // No existing groups

        when(groupService.getGroups(groupDTO.name(), groupDTO.shortName())).thenReturn(groupLookup);
        Group expectedGroup = new Group();
        expectedGroup.setName(groupDTO.name());
        expectedGroup.setShortName(groupDTO.shortName());
        expectedGroup.setSize(0);
        when(groupRepository.save(any(Group.class))).thenReturn(expectedGroup);

        // When
        Group actualGroup = groupService.createGroup(groupDTO);

        // Then
        assertEquals(expectedGroup, actualGroup);
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void createGroup_ShouldThrowHttpClientErrorException_WhenGroupAlreadyExists() {
        // Given
        GroupDTO groupDTO = new GroupDTO("Some Country", "SC");
        List<Group> groupLookup = Collections.singletonList(new Group()); // Group already exists

        when(groupService.getGroups(groupDTO.name(), groupDTO.shortName())).thenReturn(groupLookup);

        // When
        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> groupService.createGroup(groupDTO)
        );

        // Then
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("409 A group already with the given name or short name", exception.getMessage());
        verify(groupRepository, never()).save(any(Group.class));
    }

    @Test
    void updateGroup_ShouldReturnUpdatedGroup_WhenUpdateIsValid() {
        // Given
        Integer id = existingGroup.getId();
        GroupDTO groupDTO = new GroupDTO("Updated Country", "UC");
        when(groupRepository.findById(id)).thenReturn(java.util.Optional.of(existingGroup));
        when(groupService.getGroups(groupDTO.name(), groupDTO.shortName())).thenReturn(Collections.emptyList());
        when(groupRepository.save(any(Group.class))).thenReturn(existingGroup);

        // When
        Group actualGroup = groupService.updateGroup(id, groupDTO);

        // Then
        assertEquals(existingGroup, actualGroup);
        assertEquals("Updated Country", actualGroup.getName());
        assertEquals("UC", actualGroup.getShortName());
        verify(groupRepository).save(existingGroup);
    }

    @Test
    void updateGroup_ShouldThrowHttpClientErrorException_WhenGroupAlreadyExists() {
        // Given
        Integer id = existingGroup.getId();
        GroupDTO groupDTO = new GroupDTO("Some Country", "SC"); // Existing name and short name

        // Create a mock existing group with a valid ID
        Group existingGroupWithSameName = new Group();
        existingGroupWithSameName.setId(2); // Different ID but same name and short name
        existingGroupWithSameName.setName("Some Country");
        existingGroupWithSameName.setShortName("SC");
        List<Group> groupLookup = Collections.singletonList(existingGroupWithSameName);

        when(groupRepository.findById(id)).thenReturn(java.util.Optional.of(existingGroup));
        when(groupService.getGroups(groupDTO.name(), groupDTO.shortName())).thenReturn(groupLookup);

        // When & Then
        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> groupService.updateGroup(id, groupDTO)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("409 A group already with the given name or short name", exception.getMessage());
        verify(groupRepository, never()).save(any(Group.class)); // Ensure save is not called
    }


    @Test
    void updateGroup_ShouldThrowHttpClientErrorException_WhenGroupNotFound() {
        // Given
        Integer id = 999; // Non-existing ID
        GroupDTO groupDTO = new GroupDTO("Updated Country", "UC");

        when(groupRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // When & Then
        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> groupService.updateGroup(id, groupDTO)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 No groups were found with the given ID", exception.getMessage());
        verify(groupRepository, never()).save(any(Group.class)); // Ensure save is not called
    }

    @Test
    void updateGroup_ShouldUpdateGroup_WhenGroupLookupIsEmpty() {
        // Given
        Integer id = existingGroup.getId();
        GroupDTO groupDTO = new GroupDTO("New Country", "NC"); // New name and short name

        when(groupRepository.findById(id)).thenReturn(java.util.Optional.of(existingGroup));
        when(groupService.getGroups(groupDTO.name(), groupDTO.shortName())).thenReturn(Collections.emptyList());
        when(groupRepository.save(any(Group.class))).thenReturn(existingGroup);

        // When
        Group actualGroup = groupService.updateGroup(id, groupDTO);

        // Then
        assertEquals(existingGroup, actualGroup);
        assertEquals("New Country", actualGroup.getName());
        assertEquals("NC", actualGroup.getShortName());
        verify(groupRepository).save(existingGroup);
    }


    @Test
    void deleteGroup_ShouldDeleteGroup_WhenGroupExists() {
        // Given
        Integer id = 1; // Example group ID
        Group existingGroup = new Group();
        existingGroup.setId(id);
        existingGroup.setName("Some Country");
        existingGroup.setShortName("SC");

        when(groupRepository.findById(id)).thenReturn(java.util.Optional.of(existingGroup));

        // When
        groupService.deleteGroup(id);

        // Then
        verify(groupRepository).delete(existingGroup); // Ensure delete is called with the correct group
    }

    @Test
    void deleteGroup_ShouldThrowHttpClientErrorException_WhenGroupDoesNotExist() {
        // Given
        Integer id = 1; // Example group ID
        when(groupRepository.findById(id)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(HttpClientErrorException.class, () -> groupService.deleteGroup(id));
    }
}
