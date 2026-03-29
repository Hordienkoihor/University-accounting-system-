package domain;

import Utilitys.Validator;
import exceptions.IllegalNameException;

import java.util.ArrayList;
import java.util.List;

public class Specialty {
    private String name;
    private String tag;

    private Department department;

    private List<Group> groups = new ArrayList<>();

    public Specialty(String name, String tag, Department department) {
        setName(name);
        setTag(tag);
        this.department = department;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (!Validator.isValidString(name)) {
            throw new IllegalNameException("Name must be a valid name");
        }

        this.name = name;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        if (!Validator.isValidString(tag)) {
            throw new IllegalNameException("Tag must be a valid tag");
        }

        this.tag = tag;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public String toString() {
        String depName = department != null ? department.getName() : "N/A";

        return "Specialty {" + '\n' +
                "   name='" + name + ',' + '\n' +
                "   tag='" + tag + ',' + '\n' +
                "   department=" + depName + " id " + +',' + '\n' +
                '}';
    }
}
