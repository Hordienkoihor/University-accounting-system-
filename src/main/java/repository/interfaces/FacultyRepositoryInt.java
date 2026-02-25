package repository.interfaces;

import domain.Faculty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FacultyRepositoryInt<T, ID> extends DefaultRepository<T, ID> {
    boolean existsByName(String name);

    Optional<T> findByName(String name);

    Map<ID, T> getAllAsMap();

//    void deleteByCode(String code);
    void deleteByName(String name);

}
