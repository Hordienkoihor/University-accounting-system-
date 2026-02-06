package main;

import exceptions.IllegalNameException;
import exceptions.StaffAddingError;
import exceptions.StudentAddingError;

import java.util.ArrayList;

public class Group {
    private Specialty specialty;
    private String name;

    private ArrayList<Integer> students = new ArrayList<>();

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
        if (!name.contains(specialty.getName())) {
            throw new IllegalNameException("Name of the group must contain the speciality name");
        }

        this.name = name;
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new StaffAddingError("Student cannot be null");
        }

        if (students.contains(student.getId())) {
            throw new StudentAddingError("Student already in this group");
        }

        students.add(student.getId());
    }

    public void removeStudent(Student student) {
        if (student == null) {
            throw new StaffAddingError("Student cannot be null");
        }

        students.remove(student.getId());
    }

    public ArrayList<Integer> getStudents() {
        return students;
    }
}
