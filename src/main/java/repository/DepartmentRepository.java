package repository;

import domain.Department;
import domain.Faculty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.interfaces.DepartmentRepositoryInt;
import repository.io.PersistenceService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DepartmentRepository implements DepartmentRepositoryInt {
    private final Map<String, Department> departments = new ConcurrentHashMap<>();
    private final PersistenceService<Department> persistence = new PersistenceService<>(Department.class, "departments.json");

    private final Logger log = LoggerFactory.getLogger(DepartmentRepository.class);

    public DepartmentRepository() {
        log.info("Initializing DepartmentRepository");
        List<Department> loadedData = persistence.loadAll();
        loadedData.forEach(d -> departments.put(d.getCode(), d));
        log.info("Initialized with {} departments", departments.size());
    }

    @Override
    public void save(Department entity) {
        departments.put(entity.getCode(), entity);
        log.debug("Department with code {} saved to memory", entity.getCode());
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
        if (departments.remove(s) != null) {
            log.info("Department with code {} deleted", s);
            persistence.saveAll(findAll());
        } else {
            log.warn("Attempted to delete non-existent department with code: {}", s);
        }
    }
}
