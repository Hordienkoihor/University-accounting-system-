package repository;

import domain.Department;
import domain.Faculty;
import repository.interfaces.DepartmentRepositoryInt;

import java.util.*;

public class DepartmentRepository implements DepartmentRepositoryInt {
    private final Map<String, Department> departments = new HashMap<>();

    public DepartmentRepository() {
    }

    @Override
    public void save(Department entity) {
        departments.put(entity.getCode(), entity);
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

        Faculty faculty = department.getFaculty();
        faculty.remove(department);
    }
}
