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

    /*constructor with direct indication of a specific course */
    public Student(
            String name,
            String surname,
            String fatherName,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth,
            StudyForm studyForm,
            StudyStatus studyStatus
    ) {
        super(name, surname, fatherName, email, phoneNumber, dateOfBirth);

        this.studentId = new StudentId("SF-ID-" + new Date().getTime() * random.nextInt(1, 500));
        setCourse(1);
        setStudyForm(studyForm);
        setStudyStatus(studyStatus);
    }

    public Student(
            String name,
            String surname,
            String fatherName,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth,
            int course,
            StudyForm studyForm,
            StudyStatus studyStatus
    ) {
        super(name, surname, fatherName, email, phoneNumber, dateOfBirth);

        this.studentId = new StudentId("SID-" + new Date().getTime() * random.nextInt(1, 500));
        setCourse(course);
        setStudyForm(studyForm);
        setStudyStatus(studyStatus);
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
        if (course < 1 || course > 6) {
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
        String groupName = (group != null) ? group.getName() : "N?/A";
        String deptName = (group != null && group.getDepartment() != null) ? group.getDepartment().getName() : "N/A";
        String facultyName = (group != null && group.getDepartment() != null && group.getDepartment().getFaculty() != null)
                ? group.getDepartment().getFaculty().getName() : "N/A";

        return "Student {" + '\n' +
                "   studentId=" + studentId + ',' + '\n' +
                "   full name= " + getFullName() + ',' + '\n' +
                "   dob= " + getDateOfBirth() + ',' + '\n' +
                "   faculty= " + facultyName + ',' + '\n' +
                "   department= " + deptName + ',' + '\n' +
                "   group= " + groupName + ',' + '\n' +
                "   course=" + course + ',' + '\n' +
                "   studyForm=" + studyForm + ',' + '\n' +
                "   studyStatus=" + studyStatus + ',' + '\n' +
                "} ";
    }
}
