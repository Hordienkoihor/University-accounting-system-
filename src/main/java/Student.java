import abstractClasses.Person;
import enums.StudyForm;
import enums.StudyStatus;
import exceptions.IllegalCourseException;

import java.util.Date;

public class Student extends Person {

    private static int studentIdCounter = 0;

    private int studentId;
    private int course;
    private StudyForm studyForm;
    private StudyStatus studyStatus;

    /*constructor with direct indication of a specific course */
    public Student(
            String name,
            String surname,
            String fatherName,
            int age,
            String email,
            String phoneNumber,
            Date dateOfBirth,
            StudyForm studyForm,
            StudyStatus studyStatus
    ) {
        super(name, surname, fatherName, age, email, phoneNumber, dateOfBirth);

        this.studentId = studentIdCounter++;
        this.course = 1;
        this.studyForm = studyForm;
        this.studyStatus = studyStatus;
    }

    public Student(
            String name,
            String surname,
            String fatherName,
            int age,
            String email,
            String phoneNumber,
            Date dateOfBirth,
            int course,
            StudyForm studyForm,
            StudyStatus studyStatus
    ) {
        super(name, surname, fatherName, age, email, phoneNumber, dateOfBirth);

        this.studentId = studentIdCounter++;
        this.course = course;
        this.studyForm = studyForm;
        this.studyStatus = studyStatus;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourse() {
        return course;
    }

    public StudyForm getStudyForm() {
        return studyForm;
    }

    public StudyStatus getStudyStatus() {
        return studyStatus;
    }

    public void setCourse(int course) {
        if (course < 1 || course > 5) {
            throw new IllegalCourseException("Invalid course number");
        }

        this.course = course;
    }

    public void setStudyForm(StudyForm studyForm) {
        this.studyForm = studyForm;
    }

    public void setStudyStatus(StudyStatus studyStatus) {
        this.studyStatus = studyStatus;
    }
}
