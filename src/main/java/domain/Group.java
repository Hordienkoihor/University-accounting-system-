package domain;

import domain.records.StudentId;
import exceptions.IllegalNameException;
import exceptions.StaffAddingError;
import exceptions.StudentAddingError;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private Specialty specialty;
    private String name;

    private final List<StudentId> students = new ArrayList<>();

    public Group(Specialty specialty, String name) {
        setSpecialty(specialty);
        setName(name);
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public String getName() {
        return name;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public void setName(String name) {
        if (!name.contains(specialty.getTag())) {
            throw new IllegalNameException("Name of the group must contain the speciality tag");
        }

        this.name = name;
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new StudentAddingError("Student cannot be null");
        }

        if (students.contains(student.getStudentId())) {
            throw new StudentAddingError("Student already in this group");
        }

        students.add(student.getStudentId());
    }

    public void removeStudent(Student student) {
        if (student == null) {
            throw new StaffAddingError("Student cannot be null");
        }

        students.remove(student.getStudentId());
    }

    public List<StudentId> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Group {" + '\n' +
                "   specialty=" + specialty.getName() + '\n' +
                "   name=" + name + '\'' + '\n' +
                "   students=" + students.size()  + '\n' +
                '}';
    }
}
