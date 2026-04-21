import domain.Department;
import domain.Faculty;
import exceptions.DepartmentAlreadyExistsException;
import exceptions.DepartmentNotFoundException;
import exceptions.FacultyDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.interfaces.DepartmentRepositoryInt;
import service.DepartmentService;
import service.interfaces.FacultyServiceInt;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    private DepartmentRepositoryInt repository;
    private FacultyServiceInt facultyService;
    private DepartmentService service;
    private Faculty mockFaculty;

    @BeforeEach
    void setUp() {
        repository = mock(DepartmentRepositoryInt.class);
        facultyService = mock(FacultyServiceInt.class);
        service = new DepartmentService(repository, facultyService);

        mockFaculty = mock(Faculty.class);
        when(mockFaculty.getCode()).thenReturn("#FI");
    }

    @Test
    @DisplayName("Should successfully register a new department")
    void registerSuccess() {
        Department department = mock(Department.class);
        when(department.getCode()).thenReturn("@se");
        when(department.getFaculty()).thenReturn(mockFaculty);

        when(repository.existsById("@se")).thenReturn(false);
        when(facultyService.findByCode("#FI")).thenReturn(Optional.of(mockFaculty));

        service.register(department);

        verify(repository, times(1)).save(department);
    }

    @Test
    @DisplayName("Should throw DepartmentAlreadyExistsException when code exists")
    void register_DuplicateCode_ThrowsException() {
        Department department = mock(Department.class);
        when(department.getCode()).thenReturn("@se");
        when(repository.existsById("@se")).thenReturn(true);

        assertThrows(DepartmentAlreadyExistsException.class, () -> service.register(department));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw FacultyDoesNotExistException when faculty is not found")
    void register_FacultyNotFound_ThrowsException() {
        Department department = mock(Department.class);
        when(department.getCode()).thenReturn("@se");
        when(department.getFaculty()).thenReturn(mockFaculty);

        when(repository.existsById("@se")).thenReturn(false);
        when(facultyService.findByCode("#FI")).thenReturn(Optional.empty());

        assertThrows(FacultyDoesNotExistException.class, () -> service.register(department));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should return department when searching by valid code")
    void getByCodeFound() {
        Department expected = new Department();
        expected.setCode("@se");
        when(repository.findById("@se")).thenReturn(Optional.of(expected));

        Department result = service.getByCode("@se");

        assertNotNull(result);
        assertEquals("@se", result.getCode());
    }

    @Test
    @DisplayName("Should update department if it exists")
    void updateDepartment_Success() {
        Department department = new Department();
        department.setCode("@se");

        when(repository.existsById("@se")).thenReturn(true);

        service.updateDepartment(department);

        verify(repository, times(1)).save(department);
    }

    @Test
    @DisplayName("Should throw DepartmentNotFoundException when trying to delete non-existent department")
    void deleteDepartment_NotFound_ThrowsException() {
        when(repository.existsById("@se")).thenReturn(false);

        assertThrows(DepartmentNotFoundException.class, () -> service.deleteDepartment("@se"));
        verify(repository, never()).deleteById(anyString());
    }
}