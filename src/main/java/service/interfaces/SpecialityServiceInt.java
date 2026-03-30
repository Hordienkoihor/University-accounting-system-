package service.interfaces;

import domain.Specialty;

import java.util.List;

public interface SpecialityServiceInt {
    void register(Specialty specialty);

    void update(String newName, String tag);

    Specialty findByTag(String tag);
    Specialty findByName(String name);
    List<Specialty> findAllOnDepartment(String departmentCode);

    boolean existsByTag(String tag);

    void removeByTag(String tag);

    List<Specialty> getAllSpecialties();


}
