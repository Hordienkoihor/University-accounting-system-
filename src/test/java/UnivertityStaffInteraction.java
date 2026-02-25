import domain.Student;
import domain.Teacher;
import domain.enums.ScientificDegree;
import domain.enums.StudyForm;
import domain.enums.StudyStatus;
import domain.enums.UniversityPosition;
import domain.records.StaffId;
import domain.records.StudentId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import repository.UniversityRepository;
import service.UniversityService;
import service.interfaces.UniversityServiceInt;

import java.util.Date;

public class UnivertityStaffInteraction {
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
    public void doesStaffExistByValidId(String name, String surname, String fatherName, int age, String email, String phone) {
        Teacher teacher = new Teacher(name, surname, fatherName, age, email, phone, new Date(), UniversityPosition.LECTURER, ScientificDegree.NONE, 50, 15.22);
        universityService.getUniversity().addPerson(teacher);
        StaffId staffId = teacher.getStaffId();

        Assertions.assertTrue(
                universityService.getUniversity().doesStaffExist(staffId)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Karl,Malyuk,Tarasovich,18,karl@ukr.net,0680057368",
            "Taras,Malyuk,Karlovich,29,taras@ukr.net,0680057368",
            "Makar,Malyuk,Karlovich,20,taras1@ukr.net,0680057168",
            "Lev,Malyuk,Karlovich,17,taras2@ukr.net,0680052368"
    })
    public void doesStaffExistByInValidId(String name, String surname, String fatherName, int age, String email, String phone) {
        Teacher teacher = new Teacher(name, surname, fatherName, age, email, phone, new Date(), UniversityPosition.LECTURER, ScientificDegree.NONE, 50, 15.22);
        universityService.getUniversity().addPerson(teacher);
        StaffId staffId = new StaffId("ClearlyNotAnId");


        Assertions.assertFalse(universityService.getUniversity().doesStaffExist(staffId));
    }

    @ParameterizedTest
    @CsvSource({
            "Karl,Malyuk,Tarasovich,18,karl@ukr.net,0680057368",
            "Taras,Malyuk,Karlovich,29,taras@ukr.net,0680057368",
            "Makar,Malyuk,Karlovich,20,taras1@ukr.net,0680057168",
            "Lev,Malyuk,Karlovich,17,taras2@ukr.net,0680052368"
    })
    public void findStaffByValidId(String name, String surname, String fatherName, int age, String email, String phone) {
        Teacher teacher = new Teacher(name, surname, fatherName, age, email, phone, new Date(), UniversityPosition.LECTURER, ScientificDegree.NONE, 50, 15.22);
        universityService.getUniversity().addPerson(teacher);
        StaffId staffId = teacher.getStaffId();

        Assertions.assertTrue(
                teacher.equals(universityService.getUniversity().findStaffById(staffId).get())
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Karl,Malyuk,Tarasovich,18,karl@ukr.net,0680057368",
            "Taras,Malyuk,Karlovich,29,taras@ukr.net,0680057368",
            "Makar,Malyuk,Karlovich,20,taras1@ukr.net,0680057168",
            "Lev,Malyuk,Karlovich,17,taras2@ukr.net,0680052368"
    })
    public void findStaffByInValidId(String name, String surname, String fatherName, int age, String email, String phone) {
        Teacher teacher = new Teacher(name, surname, fatherName, age, email, phone, new Date(), UniversityPosition.LECTURER, ScientificDegree.NONE, 50, 15.22);
        universityService.getUniversity().addPerson(teacher);
        StaffId staffId = new StaffId("clearlyNotAnID");

        Assertions.assertTrue(universityService.getUniversity().findStaffById(staffId).isEmpty());
    }


}
