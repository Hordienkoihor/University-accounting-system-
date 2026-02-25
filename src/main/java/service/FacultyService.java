package service;

import domain.Faculty;
import exceptions.FacultyRegisterException;
import repository.interfaces.FacultyRepositoryInt;
import service.interfaces.FacultyServiceInt;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FacultyService implements FacultyServiceInt {
    private final FacultyRepositoryInt<Faculty, String> facultyRepository;

    public FacultyService(FacultyRepositoryInt<Faculty, String> facultyRepository) {
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
    public Faculty findByCode(String code) {
        return facultyRepository.findById(code).get();
    }

    @Override
    public Faculty findByName(String name) {
        return facultyRepository.findByName(name).get();
    }

    @Override
    public void register(Faculty faculty) {
        if (existsByCode(faculty.getCode())) {
            throw new FacultyRegisterException("Faculty with code " + faculty.getCode() + " already exists");
        }
        facultyRepository.save(faculty);
    }

    @Override
    public void update(String code, String name) {
        Optional<Faculty> oldFaculty = facultyRepository.findById(code);

        oldFaculty.ifPresent(faculty -> faculty.setName(name));
    }

    @Override
    public void deleteByCode(String code) {
        if (facultyRepository.existsById(code)) {
            facultyRepository.deleteById(code);
        }
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
