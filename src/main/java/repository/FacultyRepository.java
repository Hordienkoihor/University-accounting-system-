package repository;

import domain.Department;
import domain.Faculty;
import exceptions.FacultyRegisterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.dto.DepartmentDto;
import repository.dto.FacultyDto;
import repository.dto.TeacherDto;
import repository.interfaces.FacultyRepositoryInt;
import repository.io.PersistenceService;
import repository.mapper.FacultyMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FacultyRepository implements FacultyRepositoryInt {
    Map<String, Faculty> facultyMap = new ConcurrentHashMap<>();
    private final PersistenceService<FacultyDto> persistence = new PersistenceService<>(FacultyDto.class, "facultiesDto.json");

    private final FacultyMapper mapper = new FacultyMapper();

    private final Logger log = LoggerFactory.getLogger(FacultyRepository.class);


    public FacultyRepository() {
        log.info("Initializing FacultyRepository");
//        List<Faculty> loadedData = persistence.loadAll();
//        loadedData.forEach(d -> facultyMap.put(d.getCode(), d));
//        log.info("Initialised FacultyRepository with {} faculties", facultyMap.size());
    }

    public void initData(List<Faculty> loadedFaculties) {
        loadedFaculties.forEach(f -> facultyMap.put(f.getCode(), f));
        log.info("Initialized FacultyRepository with {} faculties", facultyMap.size());
    }

    public List<FacultyDto> loadRawDtos() {
        return persistence.loadAll();
    }

    private void saveToFile() {
        List<FacultyDto> dtos = facultyMap.values().stream()
                .map(mapper::toDto)
                .toList();
        persistence.saveAll(dtos);
    }

    /*scrapped*/
    public void add(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getCode())) {
            throw new FacultyRegisterException("Faculty with code " + faculty.getCode() + " already exists");
        }
    }

    @Override
    public void save(Faculty faculty) {
        facultyMap.put(faculty.getCode(), faculty);
        log.debug("Faculty with code {} saved to memory", faculty.getCode());
        saveToFile();
    }

    @Override
    public boolean existsById(String code) {
        return facultyMap.get(code) != null;
    }

    @Override
    public boolean existsByName(String name) {
        return facultyMap
                .values()
                .stream()
                .anyMatch(faculty -> faculty.getName().equals(name));
    }

    @Override
    public Optional<Faculty> findById(String code) {
        return Optional.ofNullable(facultyMap.get(code));
    }

    @Override
    public Optional<Faculty> findByName(String name) {
        return facultyMap
                .values()
                .stream()
                .filter(faculty -> faculty.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Faculty> findAll() {
        return facultyMap
                .values()
                .stream()
                .toList();
    }

    @Override
    public Map<String, Faculty> getAllAsMap() {
        return new HashMap<>(facultyMap);
    }

    @Override
    public void deleteById(String code) {
        if (facultyMap.remove(code) != null) {
            log.debug("Faculty with code {} deleted", code);
            saveToFile();
        } else {
            log.warn("Attempted to delete non-existent faculty with code: {}", code);
        }
    }

    @Override
    public void deleteByName(String name) {
        boolean removed = facultyMap.values().removeIf(faculty -> faculty.getName().equals(name));
        if (removed) {
            log.info("Faculty with name '{}' deleted", name);
            saveToFile();
        } else {
            log.warn("Attempted to delete non-existent faculty with name: {}", name);
        }
    }


}
