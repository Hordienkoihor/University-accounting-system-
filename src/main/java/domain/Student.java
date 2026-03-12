package domain;

import domain.abstractClasses.Person;
import domain.enums.StudyForm;
import domain.enums.StudyStatus;
import domain.records.StudentId;
import exceptions.IllegalCourseException;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class Student extends Person {
    private final Random random = new Random();
    private final StudentId studentId;
    private int course;
    private StudyForm studyForm;
    private StudyStatus studyStatus;

    private Group group;
    private Department department; //todo switch to group group

    /*constructor with direct indication of a specific course */
    public Student(
            String name,
            String surname,
            String fatherName,
            int age,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth,
            StudyForm studyForm,
            StudyStatus studyStatus
    ) {
        super(name, surname, fatherName, age, email, phoneNumber, dateOfBirth);

        this.studentId = new StudentId("SF-ID-" + new Date().getTime() * random.nextInt(1, 500));
        setCourse(1);
        setStudyForm(studyForm);
        setStudyStatus(studyStatus);
    }

    public Student(
            String name,
            String surname,
            String fatherName,
            int age,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth,
            int course,
            StudyForm studyForm,
            StudyStatus studyStatus
    ) {
        super(name, surname, fatherName, age, email, phoneNumber, dateOfBirth);

        this.studentId = new StudentId("SID-" + new Date().getTime() * random.nextInt(1, 500));
        setCourse(course);
        setStudyForm(studyForm);
        setStudyStatus(studyStatus);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public void setGroup(Group group) {
        this.group = group;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        if (course < 1 || course > 5) {
            throw new IllegalCourseException("Invalid course number");
        }

        this.course = course;
    }

    public StudyForm getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(StudyForm studyForm) {
        this.studyForm = studyForm;
    }

    public StudyStatus getStudyStatus() {
        return studyStatus;
    }

    public void setStudyStatus(StudyStatus studyStatus) {
        this.studyStatus = studyStatus;
    }

    @Override
    public String toString() {
        return "Student {" + '\n' +
                "   studentId=" + studentId + ',' + '\n' +
                "   full name= " + getFullName() + ',' + '\n' +
                "   dob= " + getDateOfBirth() + ',' + '\n' +
                "   faculty= " + getDepartment().getFaculty().getName() + ',' + '\n' +
                "   department= " + getDepartment().getName() + ',' + '\n' +
                "   course=" + course + ',' + '\n' +
                "   studyForm=" + studyForm + ',' + '\n' +
                "   studyStatus=" + studyStatus + ',' + '\n' +
                "} ";
    }
}
