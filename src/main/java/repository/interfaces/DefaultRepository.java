package repository.interfaces;

import java.util.List;
import java.util.Optional;

public interface DefaultRepository<T, ID> {
    void save(T entity);
    Optional<T> findById(ID id);
    boolean existsById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
