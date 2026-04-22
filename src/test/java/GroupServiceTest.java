import domain.Group;
import domain.Specialty;
import exceptions.GroupAlreadyExistsException;
import exceptions.GroupDoesNotExistException;
import exceptions.SpecialityDoesNotExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.interfaces.GroupRepositoryInt;
import service.GroupService;
import service.interfaces.SpecialityServiceInt;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupServiceTest {
    private GroupRepositoryInt repository;
    private SpecialityServiceInt specialityService;
    private GroupService service;
    private Specialty mockSpecialty;

    @BeforeEach
    void setUp() {
        repository = mock(GroupRepositoryInt.class);
        specialityService = mock(SpecialityServiceInt.class);
        service = new GroupService(repository, specialityService);

        mockSpecialty = mock(Specialty.class);
        when(mockSpecialty.getTag()).thenReturn("121");
    }

    @Test
    @DisplayName("Should successfully register a new group")
    void registerGroupSuccess() {
        when(repository.existsById("121-SE-11")).thenReturn(false);
        when(specialityService.findByTag("121")).thenReturn(mockSpecialty);

        service.registerGroup("121", "121-SE-11");

        verify(repository, times(1)).save(any(Group.class));
    }

    @Test
    @DisplayName("Should throw GroupAlreadyExistsException when group already exists")
    void registerGroup_Duplicate_ThrowsException() {
        when(repository.existsById("121-SE-11")).thenReturn(true);

        assertThrows(GroupAlreadyExistsException.class,
                () -> service.registerGroup("121", "121-SE-11"));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw SpecialityDoesNotExistsException when specialty is null")
    void registerGroup_SpecialtyNotFound_ThrowsException() {
        when(repository.existsById("121-SE-11")).thenReturn(false);
        when(specialityService.findByTag("121")).thenReturn(null);

        assertThrows(SpecialityDoesNotExistsException.class,
                () -> service.registerGroup("121", "121-SE-11"));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should update group name successfully")
    void updateName_Success() {
        Group existingGroup = new Group();

        Specialty specialty = mock(Specialty.class);
        when(specialty.getTag()).thenReturn("cs");
        existingGroup.setSpecialty(specialty);
        existingGroup.setName("cs-11");

        when(repository.findById("cs-11")).thenReturn(Optional.of(existingGroup));

        service.updateName("cs-11", "cs-12");

        assertEquals("cs-12", existingGroup.getName());
    }


    @Test
    @DisplayName("Should delete group by name")
    void deleteByName_Success() {
        when(repository.existsById("121-SE-11")).thenReturn(true);

        service.deleteByName("121-SE-11");

        verify(repository, times(1)).deleteById("121-SE-11");
    }

    @Test
    @DisplayName("Should return list of groups by specialty")
    void findAllBySpecialty_ShouldReturnList() {
        List<Group> list = List.of(new Group(), new Group());
        when(repository.findAllBySpecialty("121")).thenReturn(list);

        List<Group> result = service.findAllBySpecialty("121");

        assertEquals(2, result.size());
        verify(repository).findAllBySpecialty("121");
    }
}