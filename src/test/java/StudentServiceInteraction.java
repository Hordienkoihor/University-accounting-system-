import domain.Student;
import domain.enums.StudyForm;
import domain.enums.StudyStatus;
import domain.records.StudentId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import repository.*;
import repository.interfaces.FacultyRepositoryInt;
import repository.interfaces.GroupRepositoryInt;
import repository.interfaces.SpecialityRepositoryInt;
import repository.interfaces.StudentRepositoryInt;
import service.*;
import service.interfaces.*;

import java.time.LocalDate;

public class StudentServiceInteraction {
    private static UniversityServiceInt universityService;
    private static StudentServiceInt studentService;
    private static FacultyServiceInt facultyService;
    private static SpecialityServiceInt specialityService;
    private static GroupServiceInt groupService;

    @BeforeEach
    void setUp() {
        universityService = new UniversityService(new UniversityRepository());
        universityService.loadUniversity("config.csv");

        universityService = new UniversityService(new UniversityRepository());
        universityService.loadUniversity("config.csv");

        FacultyRepositoryInt facultyRepository = new FacultyRepository();
        facultyService = new FacultyService(facultyRepository);

        SpecialityRepositoryInt specialityRepository = new SpecialityRepository();
        specialityService = new SpecialityService(specialityRepository);

        GroupRepositoryInt groupRepository = new GroupRepository();
        groupService = new GroupService(groupRepository, specialityService);

        StudentRepositoryInt studentRepository = new StudentRepository();
        studentService = new StudentService(studentRepository, groupService);
    }

    @ParameterizedTest
    @CsvSource({
            "Karl,Malyuk,Tarasovich,karl@ukr.net,0680057368",
            "Taras,Malyuk,Karlovich,taras@ukr.net,0680057368",
            "Makar,Malyuk,Karlovich,taras1@ukr.net,0680057168",
            "Lev,Malyuk,Karlovich,taras2@ukr.net,0680052368"
    })
    public void doesStudentExistByValidId(String name, String surname, String fatherName, String email, String phone) {
        Student student = new Student(name, surname, fatherName, email, phone, LocalDate.now(), StudyForm.TUITION_FREE, StudyStatus.STUDYING);
        studentService.save(student);
        StudentId studentId = student.getStudentId();

        Assertions.assertTrue(
                studentService.existsById(studentId)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Karl,Malyuk,Tarasovich,karl@ukr.net,0680057368",
            "Taras,Malyuk,Karlovich,taras@ukr.net,0680057368",
            "Makar,Malyuk,Karlovich,taras1@ukr.net,0680057168",
            "Lev,Malyuk,Karlovich,taras2@ukr.net,0680052368"
    })
    public void doesStudentExistByInValidId(String name, String surname, String fatherName, String email, String phone) {
        Student student = new Student(name, surname, fatherName, email, phone, LocalDate.now(), StudyForm.TUITION_FREE, StudyStatus.STUDYING);
        studentService.save(student);
        StudentId studentId = new StudentId("clearlyNotAnID");

        Assertions.assertFalse(studentService.existsById(studentId));
    }

    @ParameterizedTest
    @CsvSource({
            "Karl,Malyuk,Tarasovich,karl@ukr.net,0680057368",
            "Taras,Malyuk,Karlovich,taras@ukr.net,0680057368",
            "Makar,Malyuk,Karlovich,taras1@ukr.net,0680057168",
            "Lev,Malyuk,Karlovich,taras2@ukr.net,0680052368"
    })
    public void findStudentByValidId(String name, String surname, String fatherName, String email, String phone) {
        Student student = new Student(name, surname, fatherName, email, phone, LocalDate.now(), StudyForm.TUITION_FREE, StudyStatus.STUDYING);
        studentService.save(student);
        StudentId studentId = student.getStudentId();

        Assertions.assertTrue(
                student.equals(studentService.findById(studentId))
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Karl,Malyuk,Tarasovich,karl@ukr.net,0680057368",
            "Taras,Malyuk,Karlovich,taras@ukr.net,0680057368",
            "Makar,Malyuk,Karlovich,taras1@ukr.net,0680057168",
            "Lev,Malyuk,Karlovich,taras2@ukr.net,0680052368"
    })
    public void findStudentByInValidId(String name, String surname, String fatherName, String email, String phone) {
        Student student = new Student(name, surname, fatherName, email, phone, LocalDate.now(), StudyForm.TUITION_FREE, StudyStatus.STUDYING);
        studentService.save(student);
        StudentId studentId = new StudentId("clearlyNotAnID");

        Assertions.assertNull(studentService.findById(studentId));
    }


}
