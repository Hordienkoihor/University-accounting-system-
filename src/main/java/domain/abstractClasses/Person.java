package domain.abstractClasses;

import Utilitys.Validator;
import domain.Faculty;
import domain.Group;
import exceptions.IllegalAgeException;
import exceptions.IllegalEmailException;
import exceptions.IllegalNameException;
import exceptions.IllegalPhoneNumberException;

import java.time.LocalDate;
import java.util.Date;

public abstract class Person {
    private static int idCounter = 0;

    private final int id;

    private String name;
    private String surname;
    private String fatherName;

    private int age;
    private LocalDate dateOfBirth;

    private String email;
    private String phoneNumber;

    private Faculty faculty;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    private Group group;

    protected Person(
            String name,
            String surname,
            String fatherName,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth
    ) {
        this.id = idCounter++;
        setName(name);
        setSurname(surname);
        setFatherName(fatherName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setDateOfBirth(dateOfBirth);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        return LocalDate.now().getYear() - getDateOfBirth().getYear();
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return getName() + " " + getSurname() + " " + getFatherName();
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalNameException("Name cannot be null or empty");
        }

        this.name = name;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.isEmpty()) {
            throw new IllegalNameException("Surname cannot be null or empty");
        }

        this.surname = surname;
    }

    public void setFatherName(String fatherName) {
        if (fatherName == null || fatherName.isEmpty()) {
            throw new IllegalNameException("Father name cannot be null or empty");
        }

        this.fatherName = fatherName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        if (!Validator.isValidEmailAddress(email)) {
            throw new IllegalEmailException("Email address is invalid");
        }

        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (!Validator.isValidPhoneNumber(phoneNumber, "UA")) {
            throw new IllegalPhoneNumberException("Phone number is invalid");
        }

        this.phoneNumber = phoneNumber;
    }

//    @Override
//    public String toString() {
//        return "{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", surname='" + surname + '\'' +
//                ", fatherName='" + fatherName + '\'' +
//                ", age=" + age +
//                ", dateOfBirth=" + dateOfBirth +
//                ", email='" + email + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                '}';
//    }


    @Override
    public String toString() {
        return "Person {" + '\n' +
                "   id=" + id + ',' + '\n' +
                "   name=" + name + ',' + '\n' +
                "   surname=" + surname + ',' + '\n' +
                "   fatherName=" + fatherName + ',' + '\n' +
                "   age=" + age + ',' + '\n' +
                "   dateOfBirth=" + dateOfBirth + ',' + '\n' +
                "   email=" + email + ',' + '\n' +
                "   phoneNumber=" + phoneNumber + ',' + '\n' +
                '}';
    }
}
