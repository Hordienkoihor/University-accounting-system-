package repository;

import domain.Faculty;
import domain.Specialty;
import repository.interfaces.SpecialityRepositoryInt;
import service.interfaces.FacultyServiceInt;

import java.util.List;

public class SpecialityRepository implements SpecialityRepositoryInt {
    private final FacultyServiceInt facultyService;

    public SpecialityRepository(FacultyServiceInt facultyService) {
        this.facultyService = facultyService;
    }

    @Override
    public void save(String code, Specialty specialty) {
        Faculty faculty = facultyService.findByCode(code);

        if (faculty != null) {
            faculty.addSpecialty(specialty);
        }
    }


//    @Override
//    public Specialty findByTag(String tag) {
//        return facultyService.getAllAsList().stream()
//                .flatMap(f -> f.getSpecialtyList().stream())
//                .filter(s -> s.getTag().equalsIgnoreCase(tag))
//                .findFirst()
//                .orElse(null);
//    }

    @Override
    public Specialty findByName(String name) {
        return facultyService.getAllAsList().stream()
                .flatMap(f -> f.getSpecialtyList().stream())
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

//    @Override
//    public boolean existsByTag(String tag) {
//        return findByTag(tag) != null;
//    }

    @Override
    public boolean existsByName(String name) {
        return findByName(name) != null;
    }

//    @Override
//    public void deleteByTag(String tag) {
//        facultyService.getAllAsList().forEach(f -> f.removeSpecialty(tag));
//    }

    @Override
    public List<Specialty> findAllOnFaculty(String code) {
        Faculty faculty = facultyService.findByCode(code);

        if (faculty != null) {
            return faculty.getSpecialtyList();
        }

        return List.of();
    }

    @Override
    public void save(Specialty entity) {
        throw new UnsupportedOperationException("Use save(facultyCode, speciality) instead");
    }

    @Override
    public Specialty findById(String tag) {
        return facultyService.getAllAsList().stream()
                .flatMap(f -> f.getSpecialtyList().stream())
                .filter(s -> s.getTag().equalsIgnoreCase(tag))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean existsById(String tag) {
        return findById(tag) != null;
    }

    @Override
    public List<Specialty> findAll() {
        return facultyService.getAllAsList().stream()
                .flatMap(f -> f.getSpecialtyList().stream())
                .toList();
    }

    @Override
    public void deleteById(String tag) {
        facultyService.getAllAsList().forEach(f -> f.removeSpecialty(tag));
    }


}
