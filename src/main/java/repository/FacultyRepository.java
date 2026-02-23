package repository;

import domain.Faculty;
import exceptions.FacultyDoesNotExistException;
import repository.interfaces.FacultyRepositoryInt;
import service.interfaces.UniversityServiceInt;

import java.util.List;
import java.util.Map;

public class FacultyRepository implements FacultyRepositoryInt {
    private UniversityServiceInt universityService;

    public FacultyRepository(UniversityServiceInt universityService) {
        this.universityService = universityService;
    }

    @Override
    public void save(Faculty faculty) {
        universityService.getUniversity().addFaculty(faculty);
    }

    @Override
    public boolean existsByCode(String code) {
        return universityService.getUniversity().doesFacultyExist(code);
    }

    @Override
    public boolean existsByName(String name) {
        return universityService.getUniversity().doesFacultyExistByName(name);
    }

    @Override
    public Faculty findByCode(String code) {
        return universityService.getUniversity().findFacultyByCode(code);
    }

    @Override
    public Faculty findByName(String name) {
        return universityService.getUniversity().findFacultyByName(name);
    }

    @Override
    public List<Faculty> getAllAsList() {
        return universityService.getUniversity().getFacultyList();
    }

    @Override
    public Map<String, Faculty> getAllAsMap() {
        return Map.of();
    }

    @Override
    public void deleteByCode(String code) {
        try {
            universityService.getUniversity().removeFaculty(findByCode(code));
        } catch (FacultyDoesNotExistException e) {
            System.out.println("Faculty with code " + code + " doesn't exist");
        }

    }

    @Override
    public void deleteByName(String name) {
        try {
            universityService.getUniversity().removeFaculty(findByName(name));
        } catch (FacultyDoesNotExistException e) {
            System.out.println("Faculty with name " + name + " doesn't exist");
        }
    }
}
