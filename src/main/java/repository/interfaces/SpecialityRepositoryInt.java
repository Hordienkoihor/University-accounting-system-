package repository.interfaces;

import domain.Faculty;
import domain.Specialty;

import java.util.List;
import java.util.Optional;

public interface SpecialityRepositoryInt extends DefaultRepository<Specialty, String> {
    void save(String code, Specialty specialty);
//
//    Specialty findByTag(String tag);
    Optional<Specialty> findByName(String name);

//    boolean existsByTag(String tag);
    boolean existsByName(String name);

//    void deleteByTag(String tag);

    List<Specialty> findAllOnFaculty(String code);

//    List<Specialty> findAll();

}
