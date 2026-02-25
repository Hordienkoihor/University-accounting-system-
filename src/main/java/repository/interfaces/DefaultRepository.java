package repository.interfaces;

import java.util.List;

public interface DefaultRepository<T, ID> {
    void save(T entity);
    T findById(ID id);
    boolean existsById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
