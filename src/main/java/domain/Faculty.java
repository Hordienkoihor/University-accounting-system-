package domain;

import Utilitys.Validator;
import domain.abstractClasses.Staff;
import domain.records.StaffId;
import exceptions.IllegalCodeException;
import exceptions.IllegalNameException;
import exceptions.StudentAddingError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Faculty {
    private final String code;
//    Map<StaffId, Staff> staffMap = new HashMap<>();
//    Map<String, Specialty> specialtyList = new HashMap<>();
//    Map<String, Department> departmentMap = new HashMap<>();
    private String name;
    private Staff dean;

    public Faculty(String name, String code) {
        setName(name);
        this.code = code;
    }

    public Faculty(String name, String code, Staff dean) {
        setName(name);
        this.code = code;
        this.dean = dean;
    }

    public Staff getDean() {
        return dean;
    }

    public void setDean(Staff dean) {
        this.dean = dean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!Validator.isValidString(name)) {
            throw new IllegalNameException("Name field cannot be empty");
        }

        this.name = name;
    }

    public String getCode() {
        return code;
    }

//    public List<Specialty> getSpecialtyList() {
//        return new ArrayList<>(specialtyList.values());
//    }

    public void addSpecialty(Specialty specialty) {
        if (specialty == null) {
            throw new IllegalNameException("Specialty cannot be null");
        }

        if (specialty.getTag() == null || specialty.getTag().isBlank()) {
            throw new IllegalCodeException("Specialty tag cannot be empty");
        }

        if (specialtyList.containsKey(specialty.getTag())) {
            throw new IllegalCodeException("Specialty with tag " + specialty.getTag() + " already exists in this faculty");
        }

        specialtyList.put(specialty.getTag(), specialty);
    }

    public void updateSpecialty(Specialty specialty) {
        if (specialty == null) {
            throw new IllegalNameException("Specialty cannot be null");
        }

        if (specialty.getTag() == null || specialty.getTag().isBlank()) {
            throw new IllegalCodeException("Specialty tag cannot be empty");
        }

        if (specialtyList.containsKey(specialty.getTag())) {
            specialtyList.put(specialty.getTag(), specialty);
        }
    }

    public void addStaff(Staff staff) {
        if (staff == null) {
            throw new StudentAddingError("Staff cannot be null");
        }

        staffMap.put(staff.getStaffId(), staff);
    }

    public void removeStaff(Staff staff) {
        if (staff == null) {
            throw new StudentAddingError("Staff cannot be null");
        }

        staffMap.remove(staff.getStaffId());
    }

    public boolean containsStaff(Staff staff) {
        return staffMap.containsKey(staff.getStaffId());
    }

    public Map<StaffId, Staff> getStaffMap() {
        return staffMap;
    }

    public void removeSpecialty(String tag) {
        specialtyList.remove(tag);
    }

    public Specialty getSpecialty(String tag) {
        return specialtyList.get(tag);
    }

    @Override
    public String toString() {
        return "Faculty {" + '\n' +
                "   name='" + name + ',' + '\n' +
                "   code='" + code + ',' + '\n' +
                "   dean=" + dean.getStaffId() + " " + dean.getFullName() + '\n' +
                '}';
    }

    public void add(Department department) {
        if (department == null) {
            throw new IllegalNameException("Department cannot be null");
        }

        if (departmentMap.containsKey(department.getCode())) {
            throw new RuntimeException("Department with code " + department.getCode() + " already exists");
        }

        departmentMap.put(department.getCode(), department);
    }

    public void remove(Department department) {
        if (department == null) {
            throw new IllegalNameException("Department cannot be null");
        }

        departmentMap.remove(department.getCode());
    }
}
