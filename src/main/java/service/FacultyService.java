package service;

import domain.Faculty;
import domain.abstractClasses.Staff;
import exceptions.FacultyRegisterException;
import repository.interfaces.FacultyRepositoryInt;
import service.interfaces.FacultyServiceInt;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FacultyService implements FacultyServiceInt {
    private final FacultyRepositoryInt facultyRepository;

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

    @Override
    public List<Staff> getAllStaff(Faculty faculty) {
        return faculty
                .getStaffMap()
                .values()
                .stream()
                .sorted(Comparator.comparing(Staff::getName))
                .toList();
    }
}
