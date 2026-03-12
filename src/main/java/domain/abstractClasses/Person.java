package domain.abstractClasses;

import Utilitys.Validator;
import exceptions.IllegalAgeException;
import exceptions.IllegalEmailException;
import exceptions.IllegalNameException;
import exceptions.IllegalPhoneNumberException;

import java.time.LocalDate;

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

    protected Person(
            String name,
            String surname,
            String fatherName,
            int age,
            String email,
            String phoneNumber,
            LocalDate dateOfBirth
    ) {
        this.id = idCounter++;
        setName(name);
        setSurname(surname);
        setFatherName(fatherName);
        setAge(age);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setDateOfBirth(dateOfBirth);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (!Validator.isValidPhoneNumber(phoneNumber, "UA")) {
            throw new IllegalPhoneNumberException("Phone number is invalid");
        }

        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!Validator.isValidEmailAddress(email)) {
            throw new IllegalEmailException("Email address is invalid");
        }

        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0 || age >= 500) {
            throw new IllegalAgeException("Please enter a valid age");
        }

        this.age = age;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        if (fatherName == null || fatherName.isEmpty()) {
            throw new IllegalNameException("Father name cannot be null or empty");
        }

        this.fatherName = fatherName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname == null || surname.isEmpty()) {
            throw new IllegalNameException("Surname cannot be null or empty");
        }

        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalNameException("Name cannot be null or empty");
        }

        this.name = name;
    }

    public String getFullName() {
        return getName() + " " + getSurname() + " " + getFatherName();
    }

    public int getId() {
        return id;
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
        return "{" + '\n' +
                "   id= " + id + ',' + '\n' +
                "   full name= " + getFullName() + ',' + '\n' +
                "   age= " + age + ',' + '\n' +
                "   dateOfBirth= " + dateOfBirth + ',' + '\n' +
                "   email= " + email + ',' + '\n' +
                "   phoneNumber= " + phoneNumber + ',' + '\n' +
                '}';
    }
}
