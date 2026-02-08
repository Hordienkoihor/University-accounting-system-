package domain;

import Utilitys.Validator;
import domain.abstractClasses.Staff;
import exceptions.IllegalCodeException;
import exceptions.IllegalNameException;
import exceptions.StudentAddingError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Faculty {
    private String name;
    private final String code;

    Map<String, Staff> staffMap = new HashMap<>();

    Map<String, Specialty> specialtyList = new HashMap<>();

    public Faculty(String name, String code) {
        setName(name);
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        if (!Validator.isValidString(name)) {
            throw new IllegalNameException("Name field cannot be empty");
        }

        this.name = name;
    }

    public List<Specialty> getSpecialtyList() {
        return new ArrayList<>(specialtyList.values());
    }

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

        staffMap.put(staff.getName(), staff);
    }

    public Staff removeStaff(Staff staff) {
        if (staff == null) {
            throw new StudentAddingError("Staff cannot be null");
        }

        return staffMap.remove(staff.getName());
    }

    public boolean containsStaff(Staff staff) {
        return staffMap.containsKey(staff.getName());
    }

    public Map<String, Staff> getStaffMap() {
        return staffMap;
    }

    public boolean removeSpecialty(String tag) {
        return specialtyList.remove(tag) != null;
    }

    public Specialty getSpecialty(String tag) {
        return specialtyList.get(tag);
    }

    @Override
    public String toString() {
        return "Faculty {" + '\n' +
                "   name='" + name + ',' + '\n' +
                "   code='" + code + ',' + '\n' +
                '}';
    }
}
