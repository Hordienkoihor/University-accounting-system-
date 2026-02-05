package main;

import enums.ScientificDegree;
import enums.StudyForm;
import enums.StudyStatus;
import enums.UniversityPosition;

import java.util.Date;

public class View {
    public static void main(String[] args) {
        Student student = new Student("test", "test1", "test2", 67, "example@ukr.net", "0680057368", new Date(), StudyForm.TUITION, StudyStatus.STUDYING);
        System.out.println(student);

        Teacher teacher = new Teacher("test", "test1", "test2", 67, "example@ukr.net", "0680057368", new Date(), UniversityPosition.ASSISTANT_PROFESSOR, ScientificDegree.ASSOCIATE, 10, 20);
        System.out.println(teacher);

    }
}
