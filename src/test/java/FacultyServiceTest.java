import domain.Faculty;
import exceptions.FacultyRegisterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.interfaces.FacultyRepositoryInt;
import service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacultyServiceTest {

    private FacultyRepositoryInt repository;
    private FacultyService service;

    @BeforeEach
    void setUp() {
        repository = mock(FacultyRepositoryInt.class);
        service = new FacultyService(repository);
    }

    @Test
    @DisplayName("Should successfully register a new faculty")
    void register_Success() {
        Faculty faculty = new Faculty("FI", "#FI");
        when(repository.existsById("#FI")).thenReturn(false);

        service.register(faculty);

        verify(repository, times(1)).save(faculty);
        verify(repository, times(1)).existsById("#FI");
    }

    @Test
    @DisplayName("Should throw FacultyRegisterException when code already exists")
    void register_DuplicateCode_ThrowsException() {
        Faculty faculty = new Faculty("FI", "#FI");
        when(repository.existsById("#FI")).thenReturn(true);

//        FacultyRegisterException exception = assertThrows(
//                FacultyRegisterException.class,
//                () -> service.register(faculty)
//        );
//
//        assertTrue(exception.getMessage().contains("already exists"));
//        verify(repository, never()).save(any());
        assertThrows(FacultyRegisterException.class, () -> service.register(faculty));

        verify(repository, never()).save(any());

    }

    @Test
    @DisplayName("Should return faculty when searching by valid code")
    void findByCode_Found() {
        Faculty expected = new Faculty("FI", "#FI");
        when(repository.findById("#FI")).thenReturn(Optional.of(expected));

        Optional<Faculty> result = service.findByCode("#FI");

        assertTrue(result.isPresent());

        Faculty faculty = result.get();
        assertEquals("#FI", faculty.getCode());
        assertEquals("FI", faculty.getName());
    }

    @Test
    @DisplayName("Should update faculty name correctly")
    void update_ShouldChangeName_WhenFacultyExists() {
        Faculty existingFaculty = new Faculty("OLD", "#FI");
        when(repository.findById("#FI")).thenReturn(Optional.of(existingFaculty));

        service.update("#FI", "FI");

        assertEquals("FI", existingFaculty.getName());
    }

    @Test
    @DisplayName("Should return all faculties as list")
    void getAllAsList_ShouldReturnList() {
        List<Faculty> faculties = List.of(
                new Faculty("FICT", "Informatics"),
                new Faculty("FEE", "Electronics")
        );
        when(repository.findAll()).thenReturn(faculties);

        List<Faculty> result = service.getAllAsList();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }
}