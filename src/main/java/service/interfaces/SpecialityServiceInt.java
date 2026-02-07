package service.interfaces;

import domain.Specialty;

import java.util.List;

public interface SpecialityServiceInt {
    void register(String facultyCode, Specialty specialty);

    void update(String newName, String tag);

    Specialty findByTag(String tag);
    Specialty findByName(String name);
    List<Specialty> findAllOnFaculty(String facultyCode);

    boolean existsByTag(String tag);

    void removeByTag(String tag);


}
