import domain.Student;
import domain.enums.StudyForm;
import domain.enums.StudyStatus;
import domain.records.StudentId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import repository.*;
import repository.interfaces.*;
import service.*;
import service.interfaces.*;

import java.util.Date;

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

        FacultyRepositoryInt facultyRepository = new FacultyRepository(universityService);
        facultyService = new FacultyService(facultyRepository);

        SpecialityRepositoryInt specialityRepository = new SpecialityRepository(facultyService);
        specialityService = new SpecialityService(specialityRepository);

        GroupRepositoryInt groupRepository = new GroupRepository(specialityService);
        groupService = new GroupService(groupRepository, specialityService);

        StudentRepositoryInt studentRepository = new StudentRepository(universityService);
        studentService = new StudentService(studentRepository, groupService);
    }

    @ParameterizedTest
    @CsvSource({
            "Karl,Malyuk,Tarasovich,18,karl@ukr.net,0680057368",
            "Taras,Malyuk,Karlovich,29,taras@ukr.net,0680057368",
            "Makar,Malyuk,Karlovich,20,taras1@ukr.net,0680057168",
            "Lev,Malyuk,Karlovich,17,taras2@ukr.net,0680052368"
    })
    public void doesStudentExistByValidId(String name, String surname, String fatherName, int age, String email, String phone) {
        Student student = new Student(name, surname, fatherName, age, email, phone, new Date(), StudyForm.TUITION_FREE, StudyStatus.STUDYING);
        studentService.save(student);
        StudentId studentId = student.getStudentId();

        Assertions.assertTrue(
                studentService.existsById(studentId)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Karl,Malyuk,Tarasovich,18,karl@ukr.net,0680057368",
            "Taras,Malyuk,Karlovich,29,taras@ukr.net,0680057368",
            "Makar,Malyuk,Karlovich,20,taras1@ukr.net,0680057168",
            "Lev,Malyuk,Karlovich,17,taras2@ukr.net,0680052368"
    })
    public void doesStudentExistByInValidId(String name, String surname, String fatherName, int age, String email, String phone) {
        Student student = new Student(name, surname, fatherName, age, email, phone, new Date(), StudyForm.TUITION_FREE, StudyStatus.STUDYING);
        studentService.save(student);
        StudentId studentId = new StudentId("clearlyNotAnID");

        Assertions.assertFalse(studentService.existsById(studentId));
    }

    @ParameterizedTest
    @CsvSource({
            "Karl,Malyuk,Tarasovich,18,karl@ukr.net,0680057368",
            "Taras,Malyuk,Karlovich,29,taras@ukr.net,0680057368",
            "Makar,Malyuk,Karlovich,20,taras1@ukr.net,0680057168",
            "Lev,Malyuk,Karlovich,17,taras2@ukr.net,0680052368"
    })
    public void findStudentByValidId(String name, String surname, String fatherName, int age, String email, String phone) {
        Student student = new Student(name, surname, fatherName, age, email, phone, new Date(), StudyForm.TUITION_FREE, StudyStatus.STUDYING);
        studentService.save(student);
        StudentId studentId = student.getStudentId();

        Assertions.assertTrue(
                student.equals(studentService.findById(studentId))
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Karl,Malyuk,Tarasovich,18,karl@ukr.net,0680057368",
            "Taras,Malyuk,Karlovich,29,taras@ukr.net,0680057368",
            "Makar,Malyuk,Karlovich,20,taras1@ukr.net,0680057168",
            "Lev,Malyuk,Karlovich,17,taras2@ukr.net,0680052368"
    })
    public void findStudentByInValidId(String name, String surname, String fatherName, int age, String email, String phone) {
        Student student = new Student(name, surname, fatherName, age, email, phone, new Date(), StudyForm.TUITION_FREE, StudyStatus.STUDYING);
        studentService.save(student);
        StudentId studentId = new StudentId("clearlyNotAnID");

        Assertions.assertNull(studentService.findById(studentId));
    }


}
