package repository.dto;

import domain.enums.ScientificDegree;
import domain.enums.UniversityPosition;
import domain.records.PersonId;
import domain.records.StaffId;

import java.time.LocalDate;
import java.util.Date;

public class TeacherDto {
    private String id;
    private String name;
    private String surname;
    private String fatherName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;

    private String staffId;
    private String departmentCode;

    private UniversityPosition universityPosition;
    private ScientificDegree scientificDegree;
    private Date dateOfHiring;
    private double weeklyHours;
    private double hourlyRate;

    public TeacherDto() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public UniversityPosition getUniversityPosition() {
        return universityPosition;
    }

    public void setUniversityPosition(UniversityPosition universityPosition) {
        this.universityPosition = universityPosition;
    }

    public ScientificDegree getScientificDegree() {
        return scientificDegree;
    }

    public void setScientificDegree(ScientificDegree scientificDegree) {
        this.scientificDegree = scientificDegree;
    }

    public Date getDateOfHiring() {
        return dateOfHiring;
    }

    public void setDateOfHiring(Date dateOfHiring) {
        this.dateOfHiring = dateOfHiring;
    }

    public double getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(double weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}