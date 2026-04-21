import domain.Department;
import domain.Faculty;
import domain.Teacher;
import domain.abstractClasses.Staff;
import domain.records.StaffId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.interfaces.StaffRepositoryInt;
import service.StaffService;
import service.interfaces.FacultyServiceInt;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffServiceTest {

    private StaffRepositoryInt repository;
    private FacultyServiceInt facultyService;
    private StaffService service;

    @BeforeEach
    void setUp() {
        repository = mock(StaffRepositoryInt.class);
        facultyService = mock(FacultyServiceInt.class);
        service = new StaffService(repository, facultyService);
    }

    @Test
    @DisplayName("Should save staff member successfully")
    void saveSuccess() {
        Staff staff = mock(Teacher.class);
        when(staff.getStaffId()).thenReturn(new StaffId("ST-ID-1"));

        service.save(staff);

        verify(repository, times(1)).save(staff);
    }

    @Test
    @DisplayName("Should return staff when searching by ID")
    void findById_Found() {
        StaffId id = new StaffId("ST-ID-1");
        Staff expected = mock(Teacher.class);
        when(repository.findById(id)).thenReturn(Optional.of(expected));

        Staff result = service.findById(id);

        assertNotNull(result);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should return null when staff is not found by ID")
    void findById_NotFound() {
        StaffId id = new StaffId("ST-ID-UNKNOWN");
        when(repository.findById(id)).thenReturn(Optional.empty());

        Staff result = service.findById(id);

        assertNull(result);
    }

    @Test
    @DisplayName("Should find staff by surname ")
    void findBySurname_Success() {
        Teacher t1 = new Teacher();
        t1.setSurname("Shevchenko");
        Teacher t2 = new Teacher();
        t2.setSurname("Shevchuk");
        Teacher t3 = new Teacher();
        t3.setSurname("Kovalenko");

        when(repository.findAll()).thenReturn(List.of(t1, t2, t3));

        List<Staff> result = service.findBySurname("shev");

        assertEquals(2, result.size());
        assertTrue(result.contains(t1));
        assertTrue(result.contains(t2));
    }

    @Test
    @DisplayName("Should delete staff by ID")
    void deleteById_Success() {
        StaffId id = new StaffId("ST-ID-1");
        when(repository.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should return only teachers from all staff")
    void findAllTeachers_Success() {
        Teacher teacher1 = mock(Teacher.class);
        Teacher teacher2 = mock(Teacher.class);
        Staff nonTeacher = mock(Staff.class);

        when(repository.findAll()).thenReturn(List.of(teacher1, nonTeacher, teacher2));

        List<Teacher> result = service.findAllTeachers();

        assertEquals(2, result.size());
        assertTrue(result.contains(teacher1));
        assertTrue(result.contains(teacher2));
        assertFalse(result.contains(nonTeacher));
    }
}