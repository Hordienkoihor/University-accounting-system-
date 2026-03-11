package service.interfaces;

import domain.Faculty;
import domain.abstractClasses.Staff;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FacultyServiceInt {
    boolean existsByCode(String code);

    boolean existsByName(String name);

    Optional<Faculty> findByCode(String code);

    Faculty findByName(String name);

    void register(Faculty faculty);

    void update(String code, String name);

    void deleteByCode(String code);

    void deleteByName(String name);

    List<Faculty> getAllAsList();

    Map<String, Faculty> getAllAsMap();

    //    List<Student> getAllStudents(Faculty faculty);
    List<Staff> getAllStaff(Faculty faculty);
}
