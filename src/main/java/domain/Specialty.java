package domain;

import Utilitys.Validator;
import exceptions.IllegalNameException;

import java.util.ArrayList;
import java.util.List;

public class Specialty {
    private String name;
    private String tag;

    private final Faculty faculty;

    private List<Group> groups = new ArrayList<>();

    public Specialty(String name, String tag, Faculty faculty) {
        setName(name);
        setTag(tag);
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setName(String name) {
        if (!Validator.isValidString(name)) {
            throw new IllegalNameException("Name must be a valid name");
        }

        this.name = name;
    }

    public void setTag(String tag) {
        if (!Validator.isValidString(tag)) {
            throw new IllegalNameException("Tag must be a valid tag");
        }

        this.tag = tag;
    }

    public List<Group> getGroups() {
        return groups;
    }

    @Override
    public String toString() {
        return "Specialty {" + '\n' +
                "   name='" + name + ',' + '\n' +
                "   tag='" + tag + ',' + '\n' +
                "   faculty=" + faculty.getName() + ',' + '\n' +
                '}';
    }
}
