package repository;

import domain.Department;
import domain.Faculty;
import repository.interfaces.DepartmentRepositoryInt;
import repository.io.PersistenceService;

import java.util.*;

public class DepartmentRepository implements DepartmentRepositoryInt {
    private final Map<String, Department> departments = new HashMap<>();
    private final PersistenceService<Department> persistence = new PersistenceService<>(Department.class, "departments.json");

    public DepartmentRepository() {
        persistence.loadAll().forEach(this::save);
    }

    @Override
    public void save(Department entity) {
        departments.put(entity.getCode(), entity);
        persistence.saveAll(findAll());
    }

    @Override
    public Optional<Department> findById(String s) {
        return Optional.ofNullable(departments.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return findById(s).isPresent();
    }

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(departments.values());
    }

    @Override
    public void deleteById(String s) {
        Department department = departments.remove(s);
        persistence.saveAll(findAll());
    }
}
