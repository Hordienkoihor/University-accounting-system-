import domain.Student;
import domain.enums.StudyForm;
import domain.enums.StudyStatus;
import domain.records.StudentId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import repository.*;
import service.*;
import service.interfaces.StudentServiceInt;
import service.interfaces.UniversityServiceInt;

import java.util.Date;

public class UnivertityStudentInteraction {
    private static UniversityServiceInt universityService;


    @BeforeEach
    void setUp() {
        universityService = new UniversityService(new UniversityRepository());
        universityService.loadUniversity("config.csv");
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
        universityService.getUniversity().addPerson(student);
        StudentId studentId = student.getStudentId();

        Assertions.assertTrue(
                universityService.getUniversity().doesStudentExist(studentId)
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
        universityService.getUniversity().addPerson(student);
        StudentId studentId = new StudentId("clearlyNotAnID");

        Assertions.assertFalse(universityService.getUniversity().doesStudentExist(studentId));
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
        universityService.getUniversity().addPerson(student);
        StudentId studentId = student.getStudentId();

        Assertions.assertTrue(
                student.equals(universityService.getUniversity().findStudentById(studentId).get())
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
        universityService.getUniversity().addPerson(student);
        StudentId studentId = new StudentId("clearlyNotAnID");

        Assertions.assertTrue(universityService.getUniversity().findStudentById(studentId).isEmpty());
    }


}
