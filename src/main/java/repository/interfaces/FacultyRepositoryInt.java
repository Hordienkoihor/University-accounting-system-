package repository.interfaces;

import domain.Faculty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FacultyRepositoryInt extends DefaultRepository<Faculty, String> {
    boolean existsByName(String name);

    Optional<Faculty> findByName(String name);

    Map<String, Faculty> getAllAsMap();

//    void deleteByCode(String code);
    void deleteByName(String name);

}
