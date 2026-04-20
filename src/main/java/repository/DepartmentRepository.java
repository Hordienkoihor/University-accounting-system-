package repository;

import domain.Department;
import domain.Faculty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.dto.DepartmentDto;
import repository.dto.TeacherDto;
import repository.interfaces.DepartmentRepositoryInt;
import repository.io.PersistenceService;
import repository.mapper.DepartmentMapper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DepartmentRepository implements DepartmentRepositoryInt {
    private final Map<String, Department> departments = new ConcurrentHashMap<>();
    private final PersistenceService<DepartmentDto> persistence = new PersistenceService<>(DepartmentDto.class, "departmentsDto.json");

    private final DepartmentMapper mapper = new DepartmentMapper();

    private final Logger log = LoggerFactory.getLogger(DepartmentRepository.class);

    public DepartmentRepository() {
        log.info("Initializing DepartmentRepository");
//        List<Department> loadedData = persistence.loadAll();
//        loadedData.forEach(d -> departments.put(d.getCode(), d));
//        log.info("Initialized DepartmentRepository with {} departments", departments.size());
    }

    public void initData(List<Department> loadedDepartments) {
        loadedDepartments.forEach(d -> departments.put(d.getCode(), d));
        log.info("Initialized DepartmentRepository with {} departments", departments.size());
    }

    public List<DepartmentDto> loadRawDtos() {
        return persistence.loadAll();
    }

    @Override
    public void save(Department entity) {
        departments.put(entity.getCode(), entity);
        log.debug("Department with code {} saved to memory", entity.getCode());
        saveToFile();
    }

    private void saveToFile() {
        List<DepartmentDto> dtos = departments.values().stream()
                .map(mapper::toDto)
                .toList();
        persistence.saveAll(dtos);
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
            saveToFile();
        } else {
            log.warn("Attempted to delete non-existent department with code: {}", s);
        }
    }
}
