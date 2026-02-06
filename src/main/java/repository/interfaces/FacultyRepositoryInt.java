package repository.interfaces;

import domain.Faculty;

import java.util.List;

public interface FacultyRepositoryInt {
    void save(Faculty faculty);

    boolean existsByCode(String code);
    boolean existsByName(String name);

    Faculty findByCode(String code);
    Faculty findByName(String name);
    List<Faculty> findAll();

    void deleteByCode(String code);
    void deleteByName(String name);

}
