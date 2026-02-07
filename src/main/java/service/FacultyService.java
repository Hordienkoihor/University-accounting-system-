package service;

import domain.Faculty;
import exceptions.FacultyRegisterException;
import repository.interfaces.FacultyRepositoryInt;
import service.interfaces.FacultyServiceInt;

import java.util.List;
import java.util.Map;

public class FacultyService implements FacultyServiceInt {
    private FacultyRepositoryInt facultyRepository;

    public FacultyService(FacultyRepositoryInt facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public boolean existsByCode(String code) {
        return facultyRepository.existsByCode(code);
    }

    @Override
    public boolean existsByName(String name) {
        return facultyRepository.existsByName(name);
    }

    @Override
    public Faculty findByCode(String code) {
        return facultyRepository.findByCode(code);
    }

    @Override
    public Faculty findByName(String name) {
        return facultyRepository.findByName(name);
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
        Faculty oldFaculty = facultyRepository.findByCode(code);

        if (oldFaculty != null) {
            oldFaculty.setName(name);
        }
    }

    @Override
    public void deleteByCode(String code) {
        if (facultyRepository.existsByCode(code)) {
            facultyRepository.deleteByCode(code);
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
        return facultyRepository.getAllAsList();
    }

    @Override
    public Map<String, Faculty> getAllAsMap() {
        return facultyRepository.getAllAsMap();
    }
}
