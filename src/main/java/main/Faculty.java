package main;

import Utilitys.Validator;
import abstractClasses.Staff;
import exceptions.IllegalCodeException;
import exceptions.IllegalNameException;

import java.util.ArrayList;

public class Faculty {
    private String name;
    private String code;

    ArrayList<Staff> staffList = new ArrayList<>();

    ArrayList<Specialty> specialtyList = new ArrayList<>();

    Faculty(String name, String code) {
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
        return specialtyList;
    }

    @Override
    public String toString() {
        return "Faculty {" + '\n' +
                "   name='" + name + ',' + '\n' +
                "   code='" + code + ',' + '\n' +
                '}';
    }
}
