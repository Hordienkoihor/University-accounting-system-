package domain;

import Utilitys.Validator;
import domain.abstractClasses.Staff;
import exceptions.IllegalCodeException;
import exceptions.IllegalNameException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Faculty {
    private String name;
    private String code;

    ArrayList<Staff> staffList = new ArrayList<>();

    Map<String, Specialty> specialtyList = new HashMap<>();

    public Faculty(String name, String code) {
        setName(name);
        setCode(code);
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

    public void setCode(String code) {
        if (!Validator.isValidString(code)) {
            throw new IllegalCodeException("code field cannot be empty");
        }

        this.code = code;
    }

    public ArrayList<Specialty> getSpecialtyList() {
        return new ArrayList<>(specialtyList.values());
    }

    public void addSpecialty(Specialty specialty) {
        if (specialty == null) {
            throw new IllegalNameException("Specialty cannot be null");
        }

        specialtyList.put(specialty.getTag(), specialty);
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
