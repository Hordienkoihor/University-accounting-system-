package main;

import Utilitys.Validator;
import abstractClasses.Person;
import abstractClasses.Staff;
import exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class University {
    private Map<String, Faculty> facultyMap = new HashMap<>();
    private Map<Integer, Staff> staffMap = new HashMap<>();
    private Map<Integer, Student> studentMap = new HashMap<>();

    private String fullName;
    private String shortName;
    private String city;
    private String address;

    public University(String fullName, String shortName, String city, String address) {
        setFullName(fullName);
        setShortName(shortName);
        setCity(city);
        setAddress(address);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (!Validator.isValidString(fullName)) {
            throw new IllegalNameException("Full Name field cannot be empty");
        }

        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        if (!Validator.isValidString(shortName)) {
            throw new IllegalNameException("Short Name field cannot be empty");
        }

        this.shortName = shortName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (!Validator.isValidString(city)) {
            throw new IllegalNameException("City field cannot be empty");
        }

        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!Validator.isValidString(address)) {
            throw new IllegalNameException("address field cannot be empty");
        }

        this.address = address;
    }

    public void addFaculty(Faculty faculty) {
        if (!Validator.isValidFaculty(faculty.getName(), faculty.getCode(), this.facultyMap)) {
            throw new FacultyAddingException(
                    "Faculty with name " +
                            faculty.getName() +
                            " or " + faculty.getCode() +
                            " code " +
                            " already exists");
        }

        this.facultyMap.put(faculty.getCode(), faculty);
    }

    public Faculty findFacultyByCode(String code) {
        return this.facultyMap.get(code);
    }

    public Faculty findFacultyByName(String name) {
        return this.facultyMap.values().stream()
                .filter(faculty -> faculty.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public boolean doesFacultyExist(String code) {
        return this.facultyMap.containsKey(code);
    }

    public void removeFaculty(Faculty faculty) {
        if (!doesFacultyExist(faculty.getCode())) {
            throw new FacultyDoesNotExistException(faculty.getCode());
        }

        this.facultyMap.remove(faculty.getCode());
    }

    public void addStaff(Staff staff) {
        if (staffMap.containsKey(staff.getStaffId())) {
            throw new StaffAddingError("Staff with id " + staff.getStaffId() + " or " + staff.getFullName() + " full name " + "already exists");
        }

        staffMap.put(staff.getStaffId(), staff);
    }

    public Staff findStaffById(int staffId) {
        return this.staffMap.get(staffId);
    }

    public Staff findStaffByName(String fullName) {
        return this.staffMap.values().stream()
                .filter(staff -> staff.getFullName().equalsIgnoreCase(fullName))
                .findFirst()
                .orElse(null);
    }

    public boolean doesStaffExist(int staffId) {
        return this.staffMap.containsKey(staffId);
    }

    public void removeStaff(Staff staff) {
        if (!doesStaffExist(staff.getStaffId())) {
            throw new StaffDoesNotExistException(staff.getStaffId() + "");
        }

        this.staffMap.remove(staff.getStaffId());
    }

    public void addStudent(Student student) {
        if (studentMap.containsKey(student.getStudentId())) {
            throw new StaffAddingError("Student with id " + student.getStudentId() + " or " + student.getFullName() + " full name " + "already exists");
        }

        studentMap.put(student.getStudentId(), student);
    }

    public Student findStudentById(int studentId) {
        return this.studentMap.get(studentId);
    }

    public Student findStudentByName(String fullName) {
        return this.studentMap.values().stream()
                .filter(student -> student.getFullName().equalsIgnoreCase(fullName))
                .findFirst()
                .orElse(null);
    }

    public boolean doesStudentExist(int studentId) {
        return this.studentMap.containsKey(studentId);
    }

    public void removeStudent(Student student) {
        if (!doesStaffExist(student.getStudentId())) {
            throw new StudentDoesNotExistException(student.getStudentId() + "");
        }

        this.studentMap.remove(student.getStudentId());
    }

}
