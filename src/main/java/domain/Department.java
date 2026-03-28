package domain;

import domain.abstractClasses.Staff;

public class Department {
    private String name;
    private String code;

    private Faculty faculty;
    private Staff headOfDepartment;

    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Staff getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(Staff headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Department(String name, String code, Faculty faculty, Staff headOfDepartment, String location) {
        this.name = name;
        this.code = code;
        this.faculty = faculty;
        this.headOfDepartment = headOfDepartment;
        this.location = location;
    }

    @Override
    public String toString() {
        String facultyName = faculty == null ? "N/A" : faculty.getName();
        String headOfDepartInfo = headOfDepartment == null ? "N/A" : headOfDepartment.getName() + " id: " + headOfDepartment.getStaffId();


        return "Department{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", faculty=" + facultyName +
                ", headOfDepartment=" + headOfDepartInfo +
                ", location='" + location + '\'' +
                '}';
    }
}
