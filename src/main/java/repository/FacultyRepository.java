package repository;

import domain.Faculty;
import repository.interfaces.FacultyRepositoryInt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FacultyRepository implements FacultyRepositoryInt {
    Map<String, Faculty> facultyMap = new HashMap<>();

    public FacultyRepository() {
    }

    @Override
    public void save(Faculty faculty) {
        facultyMap.put(faculty.getCode(), faculty);
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
        this.facultyMap.remove(code);
    }

    @Override
    public void deleteByName(String name) {
        this.facultyMap
                .values()
                .removeIf(faculty -> faculty.getName().equals(name));
    }
}
