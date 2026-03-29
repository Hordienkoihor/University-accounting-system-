import domain.Department;
import domain.Faculty;
import domain.Specialty;
import exceptions.SpecialityAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.interfaces.SpecialityRepositoryInt;
import service.SpecialityService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpecialityServiceTest {

    private SpecialityRepositoryInt repository;
    private SpecialityService service;
    private Faculty mockFaculty;
    private Department mockDepartment;

    @BeforeEach
    void setUp() {
        repository = mock(SpecialityRepositoryInt.class);
        service = new SpecialityService(repository);

        mockFaculty = mock(Faculty.class);
        when(mockFaculty.getName()).thenReturn("FI");

        mockDepartment = mock(Department.class);
        when(mockDepartment.getName()).thenReturn("MATH");
    }

    @Test
    @DisplayName("successfully register a new specialty")
    void registerSuccess() {
        Specialty specialty = new Specialty("ІПЗ", "ІПЗ-1", mockDepartment);
        when(repository.existsById("ІПЗ-1")).thenReturn(false);

        service.register(specialty);

        verify(repository, times(1)).save(specialty);
    }

    @Test
    @DisplayName("throw SpecialityAlreadyExistsException when already exists")
    void registerDuplicateTag_ThrowsException() {
        Specialty specialty = new Specialty("КН", "КН-1", mockDepartment);
        when(repository.existsById("КН-1")).thenReturn(true);

        assertThrows(SpecialityAlreadyExistsException.class,
                () -> service.register(specialty));

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("return specialty by tag using Optional")
    void findByTagFound() {
        Specialty expected = new Specialty("АВІС", "АВІС-1", mockDepartment);
        when(repository.findById("АВІС-1")).thenReturn(Optional.of(expected));

        Specialty result = service.findByTag("АВІС-1");

        assertNotNull(result);
        assertEquals("АВІС-1", result.getTag());
        verify(repository).findById("АВІС-1");
    }

    @Test
    @DisplayName("update specialty name only if it exists")
    void updateShouldChangeName_WhenSpecialtyExists() {
        Specialty existing = new Specialty("Old Name", "TAG-1", mockDepartment);
        when(repository.findById("TAG-1")).thenReturn(Optional.of(existing));

        service.update("New Name", "TAG-1");

        assertEquals("New Name", existing.getName());
    }

    @Test
    @DisplayName("return list of specialties for faculty")
    void findAllOnFaculty_ShouldReturnList() {
        List<Specialty> list = List.of(
                new Specialty("Spec 1", "S1", mockDepartment),
                new Specialty("Spec 2", "S2", mockDepartment)
        );
        when(repository.findAllOnDepartment("#FI")).thenReturn(list);

        List<Specialty> result = service.findAllOnDepartment("#FI");

        assertEquals(2, result.size());
        verify(repository).findAllOnDepartment("#FI");
    }
}