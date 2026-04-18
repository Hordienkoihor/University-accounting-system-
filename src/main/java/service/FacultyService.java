package service;

import domain.Faculty;
import exceptions.FacultyDoesNotExistException;
import exceptions.FacultyRegisterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.interfaces.FacultyRepositoryInt;
import service.interfaces.FacultyServiceInt;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FacultyService implements FacultyServiceInt {
    private final FacultyRepositoryInt facultyRepository;

    private static final Logger log = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepositoryInt facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public boolean existsByCode(String code) {
        return facultyRepository.existsById(code);
    }

    @Override
    public boolean existsByName(String name) {
        return facultyRepository.existsByName(name);
    }

    @Override
    public Optional<Faculty> findByCode(String code) {
        return facultyRepository.findById(code);
    }

    @Override
    public Faculty findByName(String name) {
        return facultyRepository.findByName(name)
                .orElseThrow(() -> {
                    log.warn("Search failed: Faculty with name '{}' not found", name);
                    return new FacultyDoesNotExistException("Faculty not found with name: " + name);
                });
    }

    @Override
    public void register(Faculty faculty) {
        log.info("Attempting to register faculty: {} ({})", faculty.getName(), faculty.getCode());

        if (existsByCode(faculty.getCode())) {
            log.error("Registration failed: Faculty with code {} already exists", faculty.getCode());
            throw new FacultyRegisterException("Faculty with code " + faculty.getCode() + " already exists");
        }
        facultyRepository.save(faculty);
        log.info("Faculty registered successfully: {}", faculty.getCode());
    }

    @Override
    public void update(String code, String name) {
        log.info("Updating faculty code: {} to new name: {}", code, name);
        Faculty oldFaculty = facultyRepository.findById(code)
                .orElseThrow(() -> {
                    log.error("Update failed: Faculty with code {} not found", code);
                    return new FacultyDoesNotExistException("Faculty not found with code: " + code);
                });

        oldFaculty.setName(name);
        facultyRepository.save(oldFaculty);
        log.info("Faculty {} updated successfully", code);
    }

    @Override
    public void deleteByCode(String code) {
        log.info("Attempting to delete faculty by code: {}", code);

        if (!facultyRepository.existsById(code)) {
            log.warn("Delete failed: Faculty with code {} not found", code);
            throw new FacultyDoesNotExistException("Cannot delete: Faculty with code " + code + " not found");
        }
        facultyRepository.deleteById(code);
        log.info("Faculty {} deleted successfully", code);
    }

    @Override
    public void deleteByName(String name) {
        if (facultyRepository.existsByName(name)) {
            facultyRepository.deleteByName(name);
        }
    }

    @Override
    public List<Faculty> getAllAsList() {
        return facultyRepository.findAll();
    }

    @Override
    public Map<String, Faculty> getAllAsMap() {
        return facultyRepository.getAllAsMap();
    }

}
